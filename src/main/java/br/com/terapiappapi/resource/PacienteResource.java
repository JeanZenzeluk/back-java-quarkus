package br.com.terapiappapi.resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.com.terapiappapi.controller.PacienteController;
import br.com.terapiappapi.dto.AtividadeDTO;
import br.com.terapiappapi.dto.AtividadeRealizacaoDTO;
import br.com.terapiappapi.dto.CartaoEnfrentamentoDTO;
import br.com.terapiappapi.dto.CartaoVisualizacaoDTO;
import br.com.terapiappapi.dto.DiarioSonoDTO;
import br.com.terapiappapi.entity.Atividade;
import br.com.terapiappapi.entity.AtividadeRealizacao;
import br.com.terapiappapi.entity.CartaoEnfrentamento;
import br.com.terapiappapi.entity.DiarioSono;
import br.com.terapiappapi.entity.Paciente;
import br.com.terapiappapi.entity.Rpa;
import br.com.terapiappapi.entity.VisualizacaoCartao;
import br.com.terapiappapi.util.HorarioServidorConfig;
import br.com.terapiappapi.util.Util;

@Path("/api/paciente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PacienteResource {

	@Inject
    private PacienteController rpaController;
	
	@Inject
	JsonWebToken jwt;
	
    @POST
    @Path("/rpa")
    @Transactional
    public Response create(Rpa rpa) {
    	rpa.dtRegistro = HorarioServidorConfig.diaHoraAtualServidor();
    	
    	Rpa.persist(rpa);
        return Response.ok(rpa).status(201).build();
    }
    
    @GET
    @Path("/cartaoEnfrentamento/{id}")
    @Transactional
    public List<CartaoEnfrentamentoDTO> findAll(@PathParam("id") Long idPaciente) {
    	
    	List<CartaoEnfrentamento> cartoes = CartaoEnfrentamento.findByPacienteId(idPaciente);

    	List<CartaoEnfrentamentoDTO> cartaoListDTO = new ArrayList<CartaoEnfrentamentoDTO>();
    	
    	cartaoListDTO = CartaoEnfrentamentoDTO.transformaListaEmDTO(cartoes);
    	
    	for(CartaoEnfrentamentoDTO cartaoDTO : cartaoListDTO) 
    		if(cartaoDTO.visualizacaoCartaoList !=null) 
    			for(CartaoVisualizacaoDTO  visualizacaoDto : cartaoDTO.visualizacaoCartaoList) 
	    			if(Util.dataDeHoje(visualizacaoDto.visualizado)) 
	    				cartaoDTO.totalVisualizacaoesHoje++;
    	
    	return cartaoListDTO;
	}
    
    @POST
    @Path("/visualisacaoCartao/{idCartao}")
    @Transactional
    public Boolean salvaNovaVisualizacao(@PathParam("idCartao") Long idCartao) {
    	
    	VisualizacaoCartao v = new VisualizacaoCartao();
    	v.visualizado = HorarioServidorConfig.diaHoraAtualServidor();
    	v.cartaoEnfrentamento = CartaoEnfrentamento.findById(idCartao);
    	
    	VisualizacaoCartao.persist(v);
    	
    	return true;
	}
    
    @GET
    @Path("/atividade/{id}")
    @Transactional
    public List<AtividadeDTO> findAllAtividades(@PathParam("id") Long idPaciente) {
    	
    	List<Atividade> atividades = Atividade.findByPacienteId(idPaciente);

    	List<AtividadeDTO> atividadeListDTO = new ArrayList<AtividadeDTO>();
    	
    	atividadeListDTO = AtividadeDTO.transformaListaEmDTO(atividades);
    	
    	GregorianCalendar c = new GregorianCalendar();
		c.add(Calendar.DAY_OF_MONTH, -7);
		Date data7DiasAtras = c.getTime();
    	
    	for(AtividadeDTO atividadeDTO : atividadeListDTO) 
    		if(atividadeDTO.atividadeRealizacaoList !=null) 
    			for(AtividadeRealizacaoDTO  realizacaoDto : atividadeDTO.atividadeRealizacaoList) 
	    			if(Util.dataMaiorQue(realizacaoDto.dtHoraRealizado, data7DiasAtras)) 
	    				atividadeDTO.totalVisualizacaoesSeteDias++;
    	
    	return atividadeListDTO;
	}
    
    @POST
    @Path("/realizacaoAtividade/{idAtividade}")
    @Transactional
    public Boolean salvaNovaRealizacaoAtividade(@PathParam("idAtividade") Long idAtividade) {
    	
    	AtividadeRealizacao v = new AtividadeRealizacao();
    	v.dtHoraRealizado = HorarioServidorConfig.diaHoraAtualServidor();;
    	v.atividade = Atividade.findById(idAtividade);
    	
    	Atividade.persist(v);
    	
    	return true;
	}
    
    
    @POST
    @Path("/diarioSono")
    @Transactional
    public Response create(DiarioSonoDTO diarioSonoDTO) {
    	
    	DiarioSono diario = DiarioSonoDTO.transformaParaObjeto(diarioSonoDTO);
    	System.out.println("Paciente id: " + diarioSonoDTO.pacienteId);
    	Paciente p = Paciente.findById(diarioSonoDTO.pacienteId);
    	diario.paciente = p;
    	
    	DiarioSono.persist(diario);
        return Response.ok(DiarioSonoDTO.transformaEmDTO(diario)).status(201).build();
    }

}