package br.com.terapiappapi.dto.relatorios;

public class RpaHistoricoEmocaoDTO {

	public String hora;
	public String emocao;
	public String situacao;
	public String pensamentosAutomaticos;
	public String acredita;
	public String acao;
	
	public RpaHistoricoEmocaoDTO() {
		
	}
	
	public RpaHistoricoEmocaoDTO(String hora, String emocao, String situacao, String pensamentosAutomaticos,
			String acredita, String acao) {
		super();
		this.hora = hora;
		this.emocao = emocao;
		this.situacao = situacao;
		this.pensamentosAutomaticos = pensamentosAutomaticos;
		this.acredita = acredita;
		this.acao = acao;
	}
	
	
	
}
