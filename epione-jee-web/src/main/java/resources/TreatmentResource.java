package resources;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import entities.Treatment;
import interfaces.ReportServiceLocal;
import interfaces.TreatmentServiceLocal;
import interfaces.UserServiceLocal;

@Path("/treatments")
public class TreatmentResource {
	
	@EJB
	TreatmentServiceLocal ts ;
	@EJB
	UserServiceLocal userServ;
	@EJB
	ReportServiceLocal rs ;
	
	//à tester
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response addTreatment(Treatment treat) {
		ts.addTreatment(treat);
		return Response.status(Status.CREATED).entity(treat).build();
	}
	
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response deleteTreatment(Treatment treat) {
		ts.deleteTreatment(treat);
		return Response.status(Status.ACCEPTED).entity("Treatment Removed").build();
	}
	
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response updateTreatment(Treatment treat) {
		ts.updateTreatment(treat);
		return Response.status(Status.ACCEPTED).entity(treat).build();
	}
	
	
	
	// GETALL | SEARCH by id | by recom doctor | by desc | by  recom doctor && desc
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response findTreatment(@QueryParam("id") int id, @QueryParam("recDoctor") String recDoctor, @QueryParam("desc") String desc) {
		if(id != 0) {
			
		Treatment t = ts.getTreatmentById(id);
		return Response.status(Status.FOUND).entity(t).build();
		
		}else if ((recDoctor != null) && (desc == null)) {
			
			return	Response.status(Status.FOUND).entity(ts.getTreatmentsByRecomDoc(recDoctor)).build();
			
		}else if ((recDoctor == null) && (desc != null)) {
			
			return	Response.status(Status.FOUND).entity(ts.getTreatmentsByDesc(desc)).build();
			
		}else if ((recDoctor != null) && (desc != null)) {
			
			return	Response.status(Status.FOUND).entity(ts.getTreatmentsByRecomDocAndDesc(recDoctor, desc)).build();
			
		}else {
			
			return	Response.status(Status.FOUND).entity(ts.getAllTreatments()).build();

		}
		
	}

	
	/* à tester (roles)*/
	@POST
	@PermitAll
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
		public Response getPathsConnectedUser(/*@Context SecurityContext securityContext*/) {
		/*User u=userServ.findUser(securityContext.getUserPrincipal().getName());*/
		/*System.out.println("id patient: "+u.getId());*/
		return Response.status(Status.FOUND).entity("test khraaaaa").build();
	}
	
	

}
