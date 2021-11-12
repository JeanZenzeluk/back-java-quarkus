package br.com.terapiappapi.restclient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import br.com.terapiappapi.entity.Paciente;
import br.com.terapiappapi.util.Util;

@Path("/fcm")
public class FirebaseResource {
	
	@Inject
	@RestClient
	FirebaseService firebaseService;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("sendLocal")
	public List<FirebaseRetorno> methodName(FirebaseConfig firebaseConfig) {
		
		List<Paciente> pacienteList = Paciente.list("permiteNotificacoes = true and usuario.ativo = true");
		List<FirebaseRetorno> retornoList = new ArrayList<FirebaseRetorno>();
		
		for(Paciente pac : pacienteList) {
			if(pac.tokenPush != null) {
				
				FirebaseConfig fireConfig = new FirebaseConfig();
				fireConfig.notification = new Notification();
				fireConfig.notification.title = firebaseConfig.notification.title;
				fireConfig.notification.icon = firebaseConfig.notification.icon;
				fireConfig.notification.badge = firebaseConfig.notification.badge;
				fireConfig.notification.tag = firebaseConfig.notification.tag;
				fireConfig.notification.click_action = firebaseConfig.notification.click_action;
				
				fireConfig.registration_ids = new ArrayList<String>();
				
				
				fireConfig.registration_ids.add(pac.tokenPush);
				fireConfig.notification.body = "Olá " + Util.retornaPrimeiroNome(pac.usuario.nome) + "! "
						+ firebaseConfig.notification.body;
				
				FirebaseRetorno retorno = firebaseService.enviaPushs(fireConfig);
				retorno.paciente_id = pac.id;
				retorno.paciente_nome = pac.usuario.nome;
				retornoList.add(retorno);
			}
		}
		
		return retornoList;
	}
}
