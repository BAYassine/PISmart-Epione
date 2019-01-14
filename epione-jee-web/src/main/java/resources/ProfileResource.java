package resources;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import interfaces.ProfileServiceLocal;

@Path("/profiles")
public class ProfileResource {
	
	@EJB
	ProfileServiceLocal profileService ;
	
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPathsUserConnected(@Context SecurityContext securityContext) {
		
		
		return  Response.status(Status.ACCEPTED).entity(profileService.getPatientsProfie()).build();
	}
	
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUser")
	public Response getIdUser(@QueryParam("id") int id,@Context SecurityContext securityContext) {
		
		
		return  Response.status(Status.ACCEPTED).entity(profileService.getUserId(id)).build();
	}


}
