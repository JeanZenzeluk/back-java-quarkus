package br.com.terapiappapi.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.com.terapiappapi.controller.PsicologoController;
import br.com.terapiappapi.dto.ItemPersonalizadoDTO;
import br.com.terapiappapi.dto.PacienteDTO;
import br.com.terapiappapi.entity.Atividade;
import br.com.terapiappapi.entity.CartaoEnfrentamento;
import br.com.terapiappapi.entity.CartaoItem;
import br.com.terapiappapi.entity.Paciente;
import br.com.terapiappapi.entity.Psicologo;


@Path("/api/psicologo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PsicologoResource {

	@Inject
    private PsicologoController psicologoController;
	
	@Inject
	JsonWebToken jwt;
	
	@GET
	@Path("/paciente/{idUsuarioPsicologo}")
	@Transactional
	public List<PacienteDTO> findAll(@PathParam("idUsuarioPsicologo") Long idUsuarioPsicologo) {
		if (idUsuarioPsicologo == null) {
            throw new WebApplicationException("Id do psicólogo responsável inválido.", 422);
        }

		List<Psicologo> psicologos = Psicologo.findByUsuarioId(idUsuarioPsicologo);
		List<PacienteDTO> pacientes = new ArrayList<PacienteDTO>();
		if(psicologos != null && psicologos.size() > 0)
			pacientes = psicologoController.buscaPacientesPorPsicologoID(psicologos.get(0).id);

		return pacientes;
	}
	 	
	@DELETE
	@Path("/paciente/{idPaciente}")
	@Transactional
	public Response inativarPaciente(@PathParam("idPaciente") Long idPaciente) {
		if (idPaciente == null) {
            throw new WebApplicationException("Id do paciente inválido.", 422);
        }
		
		Boolean inativado = psicologoController.inativarPaciente(idPaciente);
      
		return Response.ok(inativado).build();
	}
	
	@POST
	@Path("/reativarPaciente/{idPaciente}")
	@Transactional
	public Response reativarPaciente(@PathParam("idPaciente") Long idPaciente) {
		if (idPaciente == null) {
            throw new WebApplicationException("Id do paciente inválido.", 422);
        }
		
		Boolean inativado = psicologoController.reativarPaciente(idPaciente);
      
		return Response.ok(inativado).build();
	}
	
	@POST
    @Path("/paciente")
    @Transactional
    public Response create(PacienteDTO pacienteDTO) {
    	
		if (pacienteDTO.psicologoId == null) {
            throw new WebApplicationException("Id do psicólogo responsável inválido.", 422);
        }
		
		Paciente paciente = PacienteDTO.transformaParaObjeto(pacienteDTO);
		List<Psicologo> psicologos = Psicologo.findByUsuarioId(pacienteDTO.psicologoId);
		paciente.psicologo = (psicologos != null && psicologos.size() > 0 ? psicologos.get(0) : null);
		paciente.usuario.ativo = true;
		paciente.usuario.perfil = "paciente";
		
		paciente.usuario.persist();
    	paciente.persist();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	
        return Response.ok(PacienteDTO.transformaEmDTO(paciente, sdf)).status(201).build();
    }
	
	@PUT
    @Path("/paciente/alteraDados")
    @Transactional
    public Response update(PacienteDTO pacienteDTO) {
    	
		if (pacienteDTO.id == null) {
            throw new WebApplicationException("Id do paciente inválido.", 422);
        }
		
		Boolean pacienteAlterado = psicologoController.alterarDadosPaciente(pacienteDTO);
    	if(pacienteAlterado)
    		return Response.ok(null).status(201).build();
    	else
    		throw new WebApplicationException("ocorreu um erro ao alterar o paciente " + pacienteDTO.nome, 422);
    }
	
	@POST
    @Path("/paciente/novoCartao")
    @Transactional
    public Response createCartao(ItemPersonalizadoDTO cartaoDTO) {
    	
		if (cartaoDTO.pacienteId == null || cartaoDTO.titulo == null || cartaoDTO.titulo.equals("")) {
            throw new WebApplicationException("Campos inválidos.", 422);
        }
		
		CartaoEnfrentamento cartaoNovo = new CartaoEnfrentamento();
		cartaoNovo.ativo = true;
		cartaoNovo.titulo = cartaoDTO.titulo;
		cartaoNovo.paciente = Paciente.findById(cartaoDTO.pacienteId);
		
    	cartaoNovo.persist();
    	
    	cartaoDTO.id = cartaoNovo.id;
    	
        return Response.ok(cartaoDTO).status(201).build();
    }
	
	@POST
    @Path("/paciente/novaAtividade")
    @Transactional
    public Response createAtividade(ItemPersonalizadoDTO atividadeDTO) {
    	
		if (atividadeDTO.pacienteId == null || atividadeDTO.titulo == null || atividadeDTO.titulo.equals("")) {
            throw new WebApplicationException("Campos inválidos.", 422);
        }
		
		Atividade atividadeNova = new Atividade();
		atividadeNova.ativo = true;
		atividadeNova.titulo = atividadeDTO.titulo;
		atividadeNova.descricao = atividadeDTO.descricao;
		atividadeNova.paciente = Paciente.findById(atividadeDTO.pacienteId);
		
    	atividadeNova.persist();
    	
    	atividadeDTO.id = atividadeNova.id;
    	
        return Response.ok(atividadeDTO).status(201).build();
    }
	
	@POST
    @Path("/paciente/novoCartaoItem")
    @Transactional
    public Response createCartaoItem(ItemPersonalizadoDTO itemCartaoDTO) {
    	
		if (itemCartaoDTO.cartaoId == null || itemCartaoDTO.descricao == null || itemCartaoDTO.descricao.equals("")) {
            throw new WebApplicationException("Campos inválidos.", 422);
        }
		
		CartaoItem itemCartaoNovo = new CartaoItem();
		itemCartaoNovo.descricao = itemCartaoDTO.descricao;
		itemCartaoNovo.cartaoEnfrentamento = CartaoEnfrentamento.findById(itemCartaoDTO.cartaoId);
		
    	itemCartaoNovo.persist();
    	
    	itemCartaoDTO.id = itemCartaoNovo.id;
    	
        return Response.ok(itemCartaoDTO).status(201).build();
    }
	
	@DELETE
	@Path("/paciente/inativarCartao/{idCartao}")
	@Transactional
	public Response inativarCartao(@PathParam("idCartao") Long idCartao) {
		if (idCartao == null) {
            throw new WebApplicationException("Id do cartão de enfrentamento inválido.", 422);
        }
		
		Boolean inativado = psicologoController.inativarCartaoEnfrentamento(idCartao);
      
		return Response.ok(inativado).build();
	}
	
	@DELETE
	@Path("/paciente/inativarAtividade/{idAtividade}")
	@Transactional
	public Response inativarAtividade(@PathParam("idAtividade") Long idAtividade) {
		if (idAtividade == null) {
            throw new WebApplicationException("Id da atividade inválido.", 422);
        }
		
		Boolean inativado = psicologoController.inativarAtividade(idAtividade);
      
		return Response.ok(inativado).build();
	}
	
	@DELETE
	@Path("/paciente/cartaoItem/{id}")
	@Transactional
	public Response delete(@PathParam("id") Long id) {
		CartaoItem cartaoEntity = CartaoItem.findById(id);
		if (cartaoEntity == null) {
			throw new WebApplicationException("CartaoItem com o id " + id + " não existe.", Response.Status.NOT_FOUND);
		}
		cartaoEntity.delete();
		return Response.status(204).build();
	}
	
	@GET
	@Path("/paciente/listaNomes/{idPsicologo}")
	@Transactional
	public List<PacienteDTO> buscaPacientesNomes(@PathParam("idPsicologo") Long idUsuarioPsicologo) {
		if (idUsuarioPsicologo == null) {
            throw new WebApplicationException("Id do psicólogo responsável inválido.", 422);
        }
		
		List<PacienteDTO> pacientes = psicologoController.buscaPacientesIdNomePorPsicologoUsuarioID(idUsuarioPsicologo);
	    
		return pacientes;
	}

	 
	
}