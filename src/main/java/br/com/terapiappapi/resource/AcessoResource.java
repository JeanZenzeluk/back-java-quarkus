package br.com.terapiappapi.resource;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.com.terapiappapi.dto.PacienteDTO;
import br.com.terapiappapi.dto.UsuarioDTO;
import br.com.terapiappapi.entity.LogAcesso;
import br.com.terapiappapi.entity.Paciente;
import br.com.terapiappapi.entity.Usuario;
import br.com.terapiappapi.util.HorarioServidorConfig;
import br.com.terapiappapi.util.TokenUtils;

@Path("/api/acesso")
@RequestScoped
public class AcessoResource {

	@Inject
	JsonWebToken jwt;
	
    @POST
    @Path("logar")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response logar(Usuario usuario) {
    	String token = "";
    	try {
    		if(usuario == null || usuario.email == null || usuario.senha == null) {
    			return Response.ok("Preencha os campos obrigatórios").status(401).build();
    		}
    		
    		Map<String, Object> params = new HashMap<>();
    		params.put("email", usuario.email);
    		params.put("senha", usuario.senha);
    		Usuario usuarioEntity = Usuario.find("email = :email and senha = :senha", params).firstResult();
    		
    		if(usuarioEntity == null) {
    			return Response.ok("Usuário não encontrado").status(401).build();
    		}
    		
    		 String claimsJson = usuarioEntity.perfil != null && usuarioEntity.perfil.equals("admin") 
    				 ? "/JwtClaims.json" : "/JwtClaimsPaciente.json";
//	         HashMap<String, Long> timeClaims = new HashMap<>();
//	             long duration = Long.parseLong("9600");
//	             long exp = TokenUtils.currentTimeInSecs() + duration;
//	             timeClaims.put(Claims.exp.name(), exp);
	         
    		 
    		Boolean permitePensamentos = false;
    		Boolean permiteCartoes = false;
    		Boolean permiteAtividades = false;
    		Boolean permiteDiarioSono = false;
    		 
    		 Long pacienteId = null;
    		 if(usuarioEntity.perfil!= null && usuarioEntity.perfil.equals("paciente")) {
	        	 Paciente pacienteEntity = Paciente.findByusuarioId(usuarioEntity.id);
	        	 pacienteId = pacienteEntity.id;
	        	 permitePensamentos = pacienteEntity.habilitaRpa;
	        	 permiteCartoes = pacienteEntity.habilitaCartaoEnfrentamento;
	        	 permiteAtividades = pacienteEntity.habilitaPlanoAtividade;
	        	 permiteDiarioSono = pacienteEntity.habilitaDiarioSono;
	         }
	         token = TokenUtils.generateTokenString(claimsJson, null);
	         
	         try {
 				LogAcesso novoLog = new LogAcesso();
 				novoLog.usuario = Usuario.findById(usuarioEntity.id);
 				novoLog.dtHoraAcesso = HorarioServidorConfig.diaHoraAtualServidor();
 				novoLog.persist();
  			}catch(Exception e) {
 				System.err.println("Erro ao salvar o log de acessos");
 				e.printStackTrace();
 			}
	         
	        return Response
	        	.ok(new UsuarioDTO(usuarioEntity.nome, token, usuarioEntity.perfil, usuarioEntity.clinica, 
	        		usuarioEntity.id, pacienteId, permitePensamentos, permiteCartoes, 
	        		permiteAtividades, permiteDiarioSono))
	        		.status(200).build();
	         
    	}catch(Exception e) {
    			e.printStackTrace();
    			
    	}
    	return Response.status(500, "Ocorreu um erro durante a requisição").build();
    }
    
    @POST
    @Path("validaEmailPrimeiroAcesso")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response validaEmailPrimeiroAcesso(Usuario usuario) {
    	try {
    		    		
    		Map<String, Object> params = new HashMap<>();
    		params.put("email", usuario.email);
    		Usuario usuarioEntity = Usuario.find("email = :email", params).firstResult();
    		
    		if(usuarioEntity == null) {
    			return Response.ok("Usuário não encontrado").status(401).build();
    		}else if(usuarioEntity.realizouPrimeiroAcesso != null 
    				&& usuarioEntity.realizouPrimeiroAcesso == true) {
    			return Response.ok("Usuário já realizou o primeiro acesso").status(401).build();
    		}else {
    			Paciente p = Paciente.findByusuarioId(usuarioEntity.id);
    			return Response
	        		 .ok(new UsuarioDTO(p.id))
	        		 .status(200).build();
    		}
    	}catch(Exception e) {
    			e.printStackTrace();
    			
    	}
    	return Response.status(500, "Ocorreu um erro durante a requisição").build();
    }

    @POST
    @Path("finalizarCadastroPaciente")
    @Transactional
    public Response finalizarCadastroPaciente(PacienteDTO pacienteDTO) {
    	
    	try{
			if (pacienteDTO.id == null) {
	            throw new WebApplicationException("Id do paciente inválido.", 422);
	        }
			System.out.println(pacienteDTO.id);
			Paciente p = Paciente.findById(pacienteDTO.id);
			
			Map<String, Object> paramsUsuarioPaciente = new HashMap<>();
			paramsUsuarioPaciente.put("id", p.usuario.id);
			paramsUsuarioPaciente.put("realizouPrimeiroAcesso", true);
			paramsUsuarioPaciente.put("appInstalado", pacienteDTO.appInstalado);
//			paramsPaciente.put("habilitaNotificacoes", pacienteDTO.habilitaNotificacoes);
			paramsUsuarioPaciente.put("senha", pacienteDTO.senha);
	    	
			Usuario.update("realizouPrimeiroAcesso = :realizouPrimeiroAcesso"
					+ ", appInstalado = :appInstalado"
//					+ ", habilitaNotificacoes = :habilitaNotificacoes"
					+ ", senha = :senha"
					+ " where id = :id", paramsUsuarioPaciente);
			
			return Response.ok(null).status(201).build();	
		}catch(Exception e) {
			throw new WebApplicationException("ocorreu um erro ao alterar o paciente " + pacienteDTO.nome, 422);
		}
    }
    
    @POST
    @Path("salvarTokenPush")
    @Transactional
    public Response salvarTokenPush(PacienteDTO pacienteDTO) {
    	
    	try{
			if (pacienteDTO.id == null || pacienteDTO.tokenPush == null) {
	            throw new WebApplicationException("Id e/ou token do paciente inválidos.", 422);
	        }
			
			Map<String, Object> paramsPaciente = new HashMap<>();
			paramsPaciente.put("id", pacienteDTO.id);
			paramsPaciente.put("tokenPush", pacienteDTO.tokenPush);
	    	
			Paciente.update("tokenPush = :tokenPush, permiteNotificacoes = true"
					+ " where id = :id", paramsPaciente);
				
			return Response.ok(null).status(201).build();	
		}catch(Exception e) {
			throw new WebApplicationException("ocorreu um erro ao salvar o token do paciente " + pacienteDTO.id, 422);
		}
    }
    
    @PUT
    @Path("alterarTokenPushPaciente")
    @Transactional
    public Response alterarTokenPushPaciente(PacienteDTO pacienteDTO) {
    	
    	try{
			if (pacienteDTO.id == null || pacienteDTO.tokenPush == null) {
	            throw new WebApplicationException("Id ou token do paciente inválido.", 422);
	        }
			
			Paciente p = Paciente.findById(pacienteDTO.id);
			
			Map<String, Object> paramsPaciente = new HashMap<>();
			paramsPaciente.put("id", pacienteDTO.id);
			paramsPaciente.put("tokenPush", pacienteDTO.tokenPush);
			
			Paciente.update("tokenPush= :tokenPush"
					+ " where id = :id", paramsPaciente);
				
			return Response.ok(null).status(201).build();	
		}catch(Exception e) {
			throw new WebApplicationException("ocorreu um erro ao alterar o paciente " + pacienteDTO.nome, 422);
		}
    }
    
    @PUT
    @Path("instalarAppPaciente")
    @Transactional
    public Response instalarAppPaciente(PacienteDTO pacienteDTO) {
    	
    	try{
			if (pacienteDTO.id == null) {
	            throw new WebApplicationException("Id do paciente inválido.", 422);
	        }
			
			Paciente p = Paciente.findById(pacienteDTO.id);
			
			Map<String, Object> paramsUsuario = new HashMap<>();
			paramsUsuario.put("id", p.usuario.id);
			
			Usuario.update("appInstalado= true"
					+ " where id = :id", paramsUsuario);
				
			return Response.ok(null).status(201).build();	
		}catch(Exception e) {
			throw new WebApplicationException("ocorreu um erro ao alterar o status de instalado do paciente " + pacienteDTO.nome, 422);
		}
    }
    
    @PUT
    @Path("habilitarNotificacoesAppPaciente")
    @Transactional
    public Response habilitarNotificacoesAppPaciente(PacienteDTO pacienteDTO) {
    	
    	try{
			if (pacienteDTO.id == null) {
	            throw new WebApplicationException("Id do paciente inválido.", 422);
	        }
			
			Paciente p = Paciente.findById(pacienteDTO.id);
			
			Map<String, Object> paramsUsuario = new HashMap<>();
			paramsUsuario.put("id", p.usuario.id);
			
			Paciente.update("permiteNotificacoes = true"
					+ " where id = :id", pacienteDTO.id);
				
			return Response.ok(null).status(201).build();	
		}catch(Exception e) {
			throw new WebApplicationException("ocorreu um erro ao alterar o status de instalado do paciente " + pacienteDTO.nome, 422);
		}
    }
    
    @GET
    @Path("tokenPaciente")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTokenPaciente() {
    	String token = "";
    	try {
	    	 String claimsJson = "/JwtClaimsPaciente.json";
	    	 
	         token = TokenUtils.generateTokenString(claimsJson, null);
	         System.out.println(token);
    	}catch(Exception e) {
    			e.printStackTrace();
    	}
        return "" + token;
    }
    
    @GET
    @Path("login")
    @RolesAllowed({"Admin", "Paciente"})
    @Produces(MediaType.TEXT_PLAIN)
    public String login() {
    	
        return "Olá tela de Login!!";
    }
    
    @GET
    @Path("dash")
    @RolesAllowed({"Admin"})
    @Produces(MediaType.TEXT_PLAIN)
    public String dash() {
    	
        return "Olá Dashboard!!";
    }
}