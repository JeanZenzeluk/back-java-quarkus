package br.com.terapiappapi.restclient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/fcm")
@RegisterRestClient
public interface FirebaseService {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("send")
	@ClientHeaderParam(name = "Authorization", value = "key=123...")
	public FirebaseRetorno enviaPushs(FirebaseConfig firebaseConfig);
}
