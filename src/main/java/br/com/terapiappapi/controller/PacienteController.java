package br.com.terapiappapi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import br.com.terapiappapi.dto.relatorios.AtividadeChartDTO;
import br.com.terapiappapi.dto.relatorios.AtividadeRelatorioDTO;
import br.com.terapiappapi.dto.relatorios.CartaoChartDTO;
import br.com.terapiappapi.dto.relatorios.CartaoRelatorioDTO;
import br.com.terapiappapi.dto.relatorios.DatasFiltroDTO;
import br.com.terapiappapi.dto.relatorios.DiarioSonoChartDTO;
import br.com.terapiappapi.dto.relatorios.DiarioSonoRelatorioDTO;
import br.com.terapiappapi.dto.relatorios.RpaChartDTO;
import br.com.terapiappapi.dto.relatorios.RpaHistoricoDTO;
import br.com.terapiappapi.dto.relatorios.RpaHistoricoEmocaoDTO;
import br.com.terapiappapi.dto.relatorios.RpaRelatorioDTO;
import br.com.terapiappapi.entity.AtividadeRealizacao;
import br.com.terapiappapi.entity.DiarioSono;
import br.com.terapiappapi.entity.Rpa;
import br.com.terapiappapi.entity.VisualizacaoCartao;
import br.com.terapiappapi.util.Util;

@ApplicationScoped
public class PacienteController {
	
	/**
	 * Relaliza a busca dos dados de RPA do pacientee organiza os mesmos
	 * no formato do relatório.
	 * @param filtro
	 * @return
	 */
	public RpaRelatorioDTO buscaRelatorioRPA(DatasFiltroDTO filtro){
		try {
			
			RpaRelatorioDTO rpaRetornoDTO = new RpaRelatorioDTO();
			
	    	SimpleDateFormat sdfComHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat sdfBrFiltro = new SimpleDateFormat("dd/MM/yyyy");
	    	SimpleDateFormat sdfApenasHoraEMinuto = new SimpleDateFormat("HH:mm");
	    	
	    	
	    	Date dataDe = sdfBrFiltro.parse(filtro.dataDe);
	    	Date dataAte = sdfBrFiltro.parse(filtro.dataAte);
	    	
	    	String dataDeStr = sdf.format(dataDe);
	    	String dataAteStr = sdf.format(dataAte);
	    	
	    	
	    	Map<String, Object> params = new HashMap<>();
			params.put("pacienteId", filtro.pacienteId);
			params.put("dataDe", sdfComHora.parse(dataDeStr + " 00:00:00.000"));
			params.put("dataAte", sdfComHora.parse(dataAteStr + " 23:59:59.999"));
	    	
			// Realiza a Busca pelos filtros
			List<Rpa> rpaList = Rpa.list("paciente.id = :pacienteId"
					+ " and dtRegistro > :dataDe"
					+ " and dtRegistro < :dataAte"
					+ " order by dtRegistro", params);
			
			// Cria a estrutura de DTO's no padrão do relatório (Para Histórico de Pensamentos)
			List<String> datasHistorico = new ArrayList<String>(); 
			Long idCount = 1L;
			
			for(Rpa rpa : rpaList) {
				String dataFormatada = sdfBrFiltro.format(rpa.dtRegistro);
				if(!datasHistorico.contains(dataFormatada)){
					datasHistorico.add(dataFormatada);
					rpaRetornoDTO.pensamentosHistorico.add(new RpaHistoricoDTO(idCount, dataFormatada));
					idCount++;
				}
			}
			
			for(Rpa rpa : rpaList) {
				String dataFormatada = sdfBrFiltro.format(rpa.dtRegistro);
				for(RpaHistoricoDTO rpaHistoricoDTO : rpaRetornoDTO.pensamentosHistorico) {
					if(rpaHistoricoDTO.dia.equals(dataFormatada)) {
						rpaHistoricoDTO.emocoes.add(new RpaHistoricoEmocaoDTO(
								sdfApenasHoraEMinuto.format(rpa.dtRegistro), rpa.emocao, rpa.situacao, 
								rpa.pensamentosAutomaticos, retornaQuantoAcreditaStr(rpa.quantoAcredita) , rpa.acao));
					}
				}
			}
			
			List<String> emocoesAdicionadas = new ArrayList<String>();

			// Cria a estrutura de DTO's no padrão do relatório (Para Chart de Emoções)
	    	for(RpaHistoricoDTO pensamento : rpaRetornoDTO.pensamentosHistorico) {
	    		for(RpaHistoricoEmocaoDTO emocao : pensamento.emocoes) {
	    			if(!emocoesAdicionadas.contains(emocao.emocao)) {
	    				rpaRetornoDTO.itemChart.add(new RpaChartDTO(emocao.emocao, 1));
	    				emocoesAdicionadas.add(emocao.emocao);
	    			}else {
	    				for(RpaChartDTO rpaChart : rpaRetornoDTO.itemChart) {
	    					if(rpaChart.emocao.equals(emocao.emocao)) {
	    						rpaChart.quantidade++;
	    					}
	    				}
	    			}
	    		}
	    	}
	    	
			
	    	for(RpaChartDTO rpaChart : rpaRetornoDTO.itemChart) {
	    		rpaChart.emocao = rpaChart.emocao + " (" + rpaChart.quantidade + ")";
	    	}
	    	
	    	
	    	return rpaRetornoDTO;
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return null;
	    	}
	}
	
	/**
	 * Relaliza a busca dos dados de diário de sono do pacientee e organiza os mesmos
	 * no formato do relatório.
	 * @param filtro
	 * @return
	 */
	public DiarioSonoRelatorioDTO buscaRelatorioDiarioSono(DatasFiltroDTO filtro){
		try {
			
			DiarioSonoRelatorioDTO diarioSonoRetornoDTO = new DiarioSonoRelatorioDTO();
			
	    	SimpleDateFormat sdfComHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat sdfBrFiltro = new SimpleDateFormat("dd/MM/yyyy");
	    	SimpleDateFormat sdfApenasHoraEMinuto = new SimpleDateFormat("HH:mm");
	    	SimpleDateFormat sdfApenasDiasEMes = new SimpleDateFormat("dd/MM");
	    	
	    	
	    	Date dataDe = sdfBrFiltro.parse(filtro.dataDe);
	    	Date dataAte = sdfBrFiltro.parse(filtro.dataAte);
	    	
	    	String dataDeStr = sdf.format(dataDe);
	    	String dataAteStr = sdf.format(dataAte);
	    	
	    	
	    	Map<String, Object> params = new HashMap<>();
			params.put("pacienteId", filtro.pacienteId);
			params.put("dataDe", sdfComHora.parse(dataDeStr + " 00:00:00"));
			params.put("dataAte", sdfComHora.parse(dataAteStr + " 23:59:59"));
	    	
			// Realiza a Busca pelos filtros
			List<DiarioSono> diarioList = DiarioSono.list("paciente.id = :pacienteId"
					+ " and dtHora >= :dataDe"
					+ " and dtHora <= :dataAte"
					+ " order by dtHora", params);
			
			for(DiarioSono diario : diarioList) {
				if(diario != null) {
				diarioSonoRetornoDTO.itemChart.add((new DiarioSonoChartDTO(
						sdfApenasDiasEMes.format(diario.dtHora), 
						"teste", // TODO Implementar 
						sdfApenasHoraEMinuto.format(diario.horaFoiDormir),
						sdfApenasHoraEMinuto.format(diario.horaQueAcordou), 
						retornaHoraMinutoPorMinutos(diario.tempoSemDormir))));
				}
			}
			
	    	return diarioSonoRetornoDTO;
	    	
		}catch(Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	

	/**
	 * Relaliza a busca dos dados de cartão de enfrentamento do pacientee e organiza os mesmos
	 * no formato do relatório.
	 * @param filtro
	 * @return
	 */
	public CartaoRelatorioDTO buscaRelatorioCartoesEnfrentamento(DatasFiltroDTO filtro){
		try {
			
			CartaoRelatorioDTO cartaoRetornoDTO = new CartaoRelatorioDTO();
			
	    	SimpleDateFormat sdfComHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat sdfBrFiltro = new SimpleDateFormat("dd/MM/yyyy");
	    	SimpleDateFormat sdfApenasHoraEMinuto = new SimpleDateFormat("HH:mm");
	    	SimpleDateFormat sdfApenasDiasEMes = new SimpleDateFormat("dd/MM");
	    	
	    	
	    	Date dataDe = sdfBrFiltro.parse(filtro.dataDe);
	    	Date dataAte = sdfBrFiltro.parse(filtro.dataAte);
	    	
	    	String dataDeStr = sdf.format(dataDe);
	    	String dataAteStr = sdf.format(dataAte);
	    	
	    	
	    	Map<String, Object> params = new HashMap<>();
			params.put("pacienteId", filtro.pacienteId);
			params.put("dataDe", sdfComHora.parse(dataDeStr + " 00:00:00"));
			params.put("dataAte", sdfComHora.parse(dataAteStr + " 23:59:59"));
			params.put("cartaoId", filtro.id);
	    	
			// Realiza a Busca pelos filtros
			List<VisualizacaoCartao> visualizacaoList = VisualizacaoCartao.list("cartaoEnfrentamento.paciente.id = :pacienteId"
					+ " and visualizado >= :dataDe"
					+ " and visualizado <= :dataAte"
					+ " and cartaoEnfrentamento.id = :cartaoId"
					+ " order by visualizado", params);
			
			List<String> dias = new ArrayList<String>();
			
			for(VisualizacaoCartao visualizacao : visualizacaoList) {
				String dataEmTexto = sdfApenasDiasEMes.format(visualizacao.visualizado);
				if(!dias.contains(dataEmTexto)) {
					dias.add(dataEmTexto);
					cartaoRetornoDTO.itemChart.add(new CartaoChartDTO(
						dataEmTexto, 
						1));
				}else {
					for(CartaoChartDTO cartao : cartaoRetornoDTO.itemChart) {
						if(cartao.data.equals(dataEmTexto)) {
							cartao.leuXVezes++;
							continue;
						}
					}
				}
			}
			
	    	return cartaoRetornoDTO;
	    	
		}catch(Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
	/**
	 * Relaliza a busca dos dados de atividades do pacientee e organiza os mesmos
	 * no formato do relatório.
	 * @param filtro
	 * @return
	 */
	public AtividadeRelatorioDTO buscaRelatorioAtividades(DatasFiltroDTO filtro){
		try {
			
			AtividadeRelatorioDTO atividadeRetornoDTO = new AtividadeRelatorioDTO();
			
	    	SimpleDateFormat sdfComHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat sdfBrFiltro = new SimpleDateFormat("dd/MM/yyyy");
	    	SimpleDateFormat sdfApenasHoraEMinuto = new SimpleDateFormat("HH:mm");
	    	SimpleDateFormat sdfApenasDiasEMes = new SimpleDateFormat("dd/MM");
	    	
	    	
	    	Date dataDe = sdfBrFiltro.parse(filtro.dataDe);
	    	Date dataAte = sdfBrFiltro.parse(filtro.dataAte);
	    	
	    	String dataDeStr = sdf.format(dataDe);
	    	String dataAteStr = sdf.format(dataAte);
	    	
	    	
	    	Map<String, Object> params = new HashMap<>();
			params.put("pacienteId", filtro.pacienteId);
			params.put("dataDe", sdfComHora.parse(dataDeStr + " 00:00:00"));
			params.put("dataAte", sdfComHora.parse(dataAteStr + " 23:59:59"));
			params.put("atividadeId", filtro.id);
	    	
			// Realiza a Busca pelos filtros
			List<AtividadeRealizacao> realizacaoList = AtividadeRealizacao.list("atividade.paciente.id = :pacienteId"
					+ " and dtHoraRealizado >= :dataDe"
					+ " and dtHoraRealizado <= :dataAte"
					+ " and atividade.id = :atividadeId"
					+ " order by dtHoraRealizado", params);
			
			Long count = 1L;
			
			List<String> diasRealizou = new ArrayList<String>();
			
			// Monta a lista de histórico de realização das atividades
			for(AtividadeRealizacao realizado : realizacaoList) {
				String diaTexto = sdfBrFiltro.format(realizado.dtHoraRealizado);
				String dataTexto = diaTexto
						+ " às " + sdfApenasHoraEMinuto.format(realizado.dtHoraRealizado); 
				atividadeRetornoDTO.itemChart.add(new AtividadeChartDTO(count, dataTexto));
				
				if(!diasRealizou.contains(diaTexto)) {
					diasRealizou.add(diaTexto);
				}
			}
			
			// Calcula os 2 camposdo gráfico de Dias Realizados X Dias Não Realizados
			Integer contadorDeDias = 1;
			boolean fechou = false;
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(dataAte);
			
			while(!fechou) {
				if(gc.getTime().compareTo(dataDe) == 1) {
					contadorDeDias++;
					gc.add(Calendar.DAY_OF_MONTH, -1);
				}else {
					fechou = true;
				}
			}
			
			atividadeRetornoDTO.diasRealizados = diasRealizou.size();
			atividadeRetornoDTO.diasNãoRealizados = contadorDeDias - atividadeRetornoDTO.diasRealizados;
			
			return atividadeRetornoDTO;
	    	
		}catch(Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
	
	public String retornaHoraMinutoPorMinutos(Integer tempoEmMinutos) {
		Long minutos = 0L;
		if(tempoEmMinutos != null)
			minutos = Long.valueOf(tempoEmMinutos);

		Long minutosOk = minutos % 60;
		Long horasOk = (minutos - minutosOk) / 60;
		
		String minutosFinal = "";
		String horasFinal = "";
		
		if(minutosOk.toString().length() == 0)
			minutosFinal = "00";
		else if(minutosOk.toString().length() == 1)
			minutosFinal = "0" + minutosOk;
		else
			minutosFinal = minutosOk.toString();
		
		if(horasOk.toString().length() == 0)
			horasFinal = "00";
		else if(horasOk.toString().length() == 1)
			horasFinal = "0" + horasOk;
		else
			horasFinal = horasOk.toString();
		
		
		return horasFinal + ":" + minutosFinal;
		
	}
	
	public String retornaQuantoAcreditaStr(Integer valor) {
		if(valor == 1)
			return "Pouco";
		else if(valor == 2)
			return "Parcialmente";
		else if(valor == 3)
			return "Muito";
		else
			return null;
	}
	
//    public PesquisaSintomas update(Long id) {
//    	System.out.println("Okk 1");
//        PesquisaSintomas pesquisaSintomasEntity = PesquisaSintomas.findById(id);
//        System.out.println("Okk 2");
//        if (pesquisaSintomasEntity == null) {
//        	System.out.println("Okk 3");
//            throw new WebApplicationException("Ops PesquisaSintomas com este id " + id + " não existe.", Response.Status.NOT_FOUND);
//        }
//        pesquisaSintomasEntity.verificado = true;
//        return pesquisaSintomasEntity;
//    }
//    /**
//     * This method is main purpose to show simple "Business" example
//     * @param pesquisaSintomas
//     * @return
//     */
//    public boolean isPesquisaSintomasNomeIsNotEmpty(PesquisaSintomas pesquisaSintomas) {
//        return pesquisaSintomas.getNome().isEmpty();
// 
//   }
}