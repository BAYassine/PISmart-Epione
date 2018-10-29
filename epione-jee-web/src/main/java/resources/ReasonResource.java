package resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import entities.Appointment;
import entities.Reason;
import interfaces.ReasonServiceLocal;

@Path("Reason")
public class ReasonResource {
	@EJB
	ReasonServiceLocal reasonServ;

	@GET
	@RolesAllowed({ "ROLE_PATIENT"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReasonBySpeciality(@QueryParam(value = "idS") int idS) {
       List<Reason> reasons=reasonServ.searchReasonBySpeciality(idS);
       if(!reasons.isEmpty())
		return (Response.status(Response.Status.FOUND).entity(reasons).build());
       
       return (Response.status(Response.Status.NOT_FOUND).entity("no reasons for this speciality").build());
	}
	@POST
	@Path("{idS}")
	@RolesAllowed("ROLE_PATIENT")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addReason(Reason r,@PathParam(value = "idS") int idS) {
		if (r != null) {
			reasonServ.addReason(r, idS);
			return Response.status(Status.CREATED).entity("Reason added").build();
		} 
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Reason not added").build();
	}
	@DELETE
	@Path("{idR}")
	@RolesAllowed("ROLE_PATIENT")
	@Consumes(MediaType.APPLICATION_XML)
	public Response DeleteReason(@PathParam(value = "idR") int idR) {
		if (idR !=0) {
			reasonServ.removeReason(idR);
			return Response.status(Status.OK).entity("Reason deleted").build();
		} 
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Reason not deleted").build();
	}
	@PUT
	@RolesAllowed("ROLE_PATIENT")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateReason(Reason r,@QueryParam(value="idS")int idS){
		if(r!=null){
			reasonServ.updateReason(r,idS);
			return Response.status(Status.OK).entity("Reason Updated").build();
		} 
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Reason not updated").build();
	}
	
}
