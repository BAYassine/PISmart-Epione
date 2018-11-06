package resources;

import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import entities.Reason;
import entities.User;
import interfaces.ReasonServiceLocal;
import interfaces.UserServiceLocal;

@Path("Reason")
public class ReasonResource {
	@EJB
	ReasonServiceLocal reasonServ;
	@EJB
	UserServiceLocal userServ;

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
	@Consumes(MediaType.APPLICATION_JSON)
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
	@Consumes(MediaType.APPLICATION_JSON)
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
	
	
	/*Fares*/
	
	
	
	@GET
	@Path("SearchReasonsByDoctor")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReasonByDoctor(@QueryParam(value = "idDoctor") int idDoctor) {
	   Set<Reason> reasons=reasonServ.searchReasonByDoctor(idDoctor);
	   System.out.println(reasons);
	   if(reasons.isEmpty()){
		   return (Response.status(Response.Status.NOT_FOUND).entity("This doctor hasn't yet chosen his reasons").build());}
	   else 
		   return (Response.status(Response.Status.FOUND).entity(reasons).build());
	}
	@POST
	@Path("ReasonAdding")
	@RolesAllowed("ROLE_DOCTOR")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addReason(Reason r) {
		if (r != null) {
			reasonServ.addReason(r);
			return Response.status(Status.CREATED).entity("Reason added").build();
		} 
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Reason not added").build();
	}
	@Path("chosenReasons")
	@POST
	@RolesAllowed("ROLE_DOCTOR")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response chosenReasons(List<Integer> idsReasons,/*@QueryParam(value="idDoctor")int idDoctor*/@Context SecurityContext securityContext) {
		User u = userServ.findUser(securityContext.getUserPrincipal().getName());
		return Response.ok(reasonServ.chosenReasons(idsReasons, u.getId())).build();
	}
	@Path("getReasonById")
	@GET
	@RolesAllowed("ROLE_DOCTOR")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReasonById(@QueryParam(value = "idReason") int idReason) {
		return Response.ok(reasonServ.getReasonById(idReason)).build();
	}
	
	@Path("updateReasons")
	@POST
	@RolesAllowed("ROLE_DOCTOR")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateReasons(List<Integer> idsReasons,@Context SecurityContext securityContext) {
		User u = userServ.findUser(securityContext.getUserPrincipal().getName());
		return Response.ok(reasonServ.newReasons(idsReasons, u.getId())).build();
	}
	@Path("updateReasonName")
	@POST
	@RolesAllowed("ROLE_DOCTOR")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateReasonName(@QueryParam(value="name") String name,@QueryParam(value="newName") String newName) {
		return Response.ok(reasonServ.getReasonByName(name, newName)).build();
	}
		
	
	
	
	
}
