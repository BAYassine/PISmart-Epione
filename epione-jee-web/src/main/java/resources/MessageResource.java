package resources;

import java.text.ParseException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import entities.Message;
import entities.User;
import interfaces.MessageServiceLocal;
import interfaces.UserServiceLocal;

@Path("Message")
public class MessageResource {
		  @EJB
		  MessageServiceLocal messagServ;
		  @EJB
		  UserServiceLocal userServ;
		  
		    @GET
			@RolesAllowed("ROLE_PATIENT")
			@Produces(MediaType.APPLICATION_JSON)
			public Response getMessages(@Context SecurityContext securityContext){
				User u=userServ.findUser(securityContext.getUserPrincipal().getName());
				List<Message> messages=messagServ.PatientMessages(u.getId(),3);
				if(!messages.isEmpty())
					return Response.status(Status.OK).entity(messages).build();
				return Response.status(Status.NOT_FOUND).entity("No messages").build();
		  }
		    @POST
			@RolesAllowed("ROLE_PATIENT")
			@Consumes(MediaType.APPLICATION_JSON)
			public Response sendMessage(@Context SecurityContext securityContext,Message msg,@QueryParam(value="idD") int idD){
		    	if(msg!=null){
		    		User u=userServ.findUser(securityContext.getUserPrincipal().getName());
					int id=messagServ.sendMessagePatient(u.getId(), idD, msg);
					if(id!=0)
						return Response.status(Status.OK).entity("Message is sent").build();
		    	}
			
				return Response.status(Status.NOT_ACCEPTABLE).entity("Can't send message.").build();
		  }
}
