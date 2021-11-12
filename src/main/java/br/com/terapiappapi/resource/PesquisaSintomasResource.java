package br.com.terapiappapi.resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.com.terapiappapi.controller.PesquisaSintomasController;

@Path("/pesquisaSintomas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PesquisaSintomasResource {

	@Inject
    private PesquisaSintomasController pesquisaSintomasController;
	
	@Inject
	JsonWebToken jwt;
//	
//    @GET
////    @PermitAll
//    @RolesAllowed("Admin")
//    public List<PesquisaSintomas> findAll() {
//    	
//    	List<PesquisaSintomas> lista = PesquisaSintomas.listAll();
//    	
//    	for(PesquisaSintomas pesquisa : lista) {
//    		
//    		if(pesquisa.pacienteCadastro != null) {
//    			System.out.println("11111");
//    			pesquisa.pacienteCadastro.usuario.pacienteCadastroList = null;
//    			pesquisa.pacienteCadastro.pesquisaSintomasList = null;
//    			if(pesquisa.verificado == null)
//    				pesquisa.verificado = false;
////    			if(pesquisa.pacienteCadastro.usuario != null) {
////    				System.out.println("22222");
////    				pesquisa.pacienteCadastro.usuario.pacienteCadastroList = null;
////    			}
//    			
//    		}
//    		System.out.println("verificado = " + pesquisa.verificado);
//    	}
//    	
//        return lista;
//    }
////    @POST
////    @Transactional
////    @RolesAllowed("Admin")
////    public Response create(Aluno aluno) {
////        Aluno.persist(aluno);
////        return Response.ok(aluno).status(201).build();
////    }
//    @POST
//    @Transactional
//    @RolesAllowed("Paciente")
//    public Response create(PesquisaSintomas pesquisaSintomas) {
//            	
//    	Map<String, Object> params = new HashMap<>();
//		params.put("id", pesquisaSintomas.pacienteCadastro.usuario.id);
//    	
//		PacienteCadastro pacienteEntity = PacienteCadastro.find("usuario.id = :id", params).firstResult();
//    	pesquisaSintomas.pacienteCadastro = pacienteEntity;
//    	
//    	pesquisaSintomas.dtResposta = new Date();
//    	
//    	PesquisaSintomas.persist(pesquisaSintomas);
//
//    	return Response.ok(null).status(201).build();
//    }
//    
//    @PUT
//    @Path("{id}")
//    @Transactional
//    @RolesAllowed("Admin")
//    public Response update(@PathParam("id") Long id) {
//
//    	   System.out.println("Id: " + id);
//       	PesquisaSintomas.update("verificado = true where id = ?1", id);
//       	
//       	PesquisaSintomas pesquisaEntity = pesquisaSintomasController.update(id);
//   			System.out.println("11111");
//  
//           System.out.println("2222");
//       	return Response.ok(null).status(200).build();
//    	
////    	PesquisaSintomas pesquisaEntity = pesquisaSintomasController.update(id);
////        
////        if(pesquisaEntity.pacienteCadastro != null) {
////			System.out.println("11111");
////			pesquisaEntity.pacienteCadastro.usuario.pacienteCadastroList = null;
////			pesquisaEntity.pacienteCadastro.pesquisaSintomasList = null;
////		}
////        
////        return Response.ok(pesquisaEntity).build();
//    }
//    @DELETE
//    @Path("{id}")
//    @Transactional
//    @RolesAllowed("Admin")
//    public Response delete(@PathParam("id") Long id) {
//        Aluno alunoEntity = Aluno.findById(id);
//        if (alunoEntity == null) {
//            throw new WebApplicationException("Aluno with id " + id + " does not exist.", Response.Status.NOT_FOUND);
//        }
//        alunoEntity.delete();
//        return Response.status(204).build();
//    }
	
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String hello() {
//        return "hello";
//    }
}