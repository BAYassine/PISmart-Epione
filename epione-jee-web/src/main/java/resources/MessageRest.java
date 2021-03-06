package resources;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.ChatMessage;
import entities.Key;


@Path("/messages/ques")
@RequestScoped
public class MessageRest {
    @Inject
    ServerEndPointQuest webSocketEndpoint;
    
    @POST
    @Path("/send")
    public void sendMessage(@FormParam("key") String key, @FormParam("message") String message,@FormParam("doctorName") String doctorName ) {
    	ChatMessage msg = new ChatMessage();
    	msg.setContent(message);
    	msg.setDoctorName(doctorName);
    	msg.setPatientName(" ");	
    	msg.setIsPatient(" ");
    	System.out.println(msg.toString());
    	System.out.println("keyyyy"+key);
        webSocketEndpoint.send(msg, key);
    }
        /*User user = Connected_User.getUser();
    	if(user instanceof Patient){
    		String key = user.getUsername();
    		webSocketEndpoint.send(message, key);
    	}*/

    
    @GET
    @Path("/PatientsQuestions")
    @Produces(MediaType.APPLICATION_JSON)
    
    public List<Key> Patients() {
    	List<Key> keys = new ArrayList<Key>();
    	keys.addAll(webSocketEndpoint.getPatientId());
    	return keys;
    }
}