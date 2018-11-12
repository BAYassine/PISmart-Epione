package resources;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import entities.Rating;
import entities.User;
import interfaces.RateServiceLocal;
import interfaces.UserServiceLocal;


	
@Path("/rating")
public class RatingRessource {
	
	@EJB
	RateServiceLocal rateManager;
	@EJB
	UserServiceLocal usersManager;
	
	@Path("/add/{appId}")
	@POST
	@RolesAllowed({ "ROLE_PATIENT"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRate(Rating r,@PathParam("appId") int appId){
		return Response.ok(rateManager.AddRate(r,appId)).build();
	}
	
	@Path("get")
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(){
		return Response.ok(rateManager.getAllRates()).build();
	}
	
	@Path("get/{appId}")
	@GET
	@RolesAllowed({ "ROLE_PATIENT"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRateByAppointement(@PathParam("appId") int appId){
		return Response.ok(rateManager.getRateByAppoitement(appId)).build();
	}
	
	@Path("edit")
	@PUT
	@RolesAllowed({ "ROLE_PATIENT"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response EditRate(Rating r) {
		return Response.ok(rateManager.EditRate(r)).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	@RolesAllowed({ "ROLE_PATIENT"})
	public Response deleteRate (@PathParam("id") int id,@Context SecurityContext securityContext )
	{
		User u = usersManager.findUser(securityContext.getUserPrincipal().getName());
		return Response.ok(rateManager.deleteRate(id,u.getId())).build();
	}
	
	@Path("patient/{paientUserName}")
	@GET
	@RolesAllowed({ "ROLE_PATIENT"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRatesByPatientUserName(@PathParam("paientUserName") String paientUserName){
		return Response.ok(rateManager.getRatesByPatient(paientUserName)).build();
	}
	
	@Path("doctor/{doctorUserName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRatesByDoctorUserName(@PathParam("doctorUserName") String doctorUserName){
		return Response.ok(rateManager.getRatesByDoctor(doctorUserName)).build();
	}
	
	@Path("doctorRate/{doctorId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response DoctorRate(@PathParam("doctorId") int doctorId){
		return Response.ok(rateManager.DoctorRate(doctorId)).build();
	}

	
	@GET
    @RolesAllowed({ "ROLE_PATIENT", "ROLE_DOCTOR" })
	@Produces("application/json")
	public Response getAllRate() {
		List<Rating> lr = rateManager.getAllRate();
		return Response.status(Response.Status.FOUND).entity(lr).build();
	}

}
