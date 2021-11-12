package br.com.terapiappapi.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.com.terapiappapi.controller.PacienteController;
import br.com.terapiappapi.dto.relatorios.AtividadeRelatorioDTO;
import br.com.terapiappapi.dto.relatorios.CartaoRelatorioDTO;
import br.com.terapiappapi.dto.relatorios.DatasFiltroDTO;
import br.com.terapiappapi.dto.relatorios.DiarioSonoRelatorioDTO;
import br.com.terapiappapi.dto.relatorios.RpaRelatorioDTO;

@Path("/api/paciente/relatorio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PacienteRelatorioResource {

	@Inject
    private PacienteController pacienteController;
	
	@Inject
	JsonWebToken jwt;
    
    @POST
    @Path("/rpa")
    @Transactional
    public RpaRelatorioDTO buscaRelatorioRPA(DatasFiltroDTO filtro) {
    	return pacienteController.buscaRelatorioRPA(filtro);
    }
    
    @POST
    @Path("/diarioSono")
    @Transactional
    public DiarioSonoRelatorioDTO buscaRelatorioDiarioSono(DatasFiltroDTO filtro) {
    	return pacienteController.buscaRelatorioDiarioSono(filtro);
    }
    
    @POST
    @Path("/cartoes")
    @Transactional
    public CartaoRelatorioDTO buscaRelatorioCartoesEnfrentamento(DatasFiltroDTO filtro) {
    	return pacienteController.buscaRelatorioCartoesEnfrentamento(filtro);
    }
    
    @POST
    @Path("/atividades")
    @Transactional
    public AtividadeRelatorioDTO buscaRelatorioAtividades(DatasFiltroDTO filtro) {
    	return pacienteController.buscaRelatorioAtividades(filtro);
    }
    
    
}