package resources;

import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import entities.Consultation;
import entities.Doctor;
import entities.Reason;
import entities.User;
import interfaces.ConsultationServiceLocal;
import interfaces.DoctorServiceLocal;
import interfaces.UserServiceLocal;
import services.ConsultationService;

@Path("Consultation")
public class ConsultationRessource {
	@EJB
	ConsultationServiceLocal consService;
	
	@EJB
	UserServiceLocal userServ;
	
	@EJB
	DoctorServiceLocal doctorServ;
	
	@POST
	@Path("ConsultationAdding")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addConsultation(Consultation c) {
		if (c.getRating()<=5) {
			consService.addConsultation(c);
			return Response.status(Status.OK).entity("Consultation added").build();
						}
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Consultation cannot be added (Rating must be a number from 1 to 5)").build();
	}

	@DELETE
	@Path("Delete")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteConsultation(@QueryParam(value = "idC") int idC) {
		if (idC !=0) {
			consService.deleteConsultation(idC);
			return Response.status(Status.OK).entity("Consultation deleted").build();
		} 
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Consultation couldn't be deleted").build();
	}
	@Path("updateConsultation")
	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateConsultation(Consultation c) {
		consService.updateConsultation(c);
		return Response.status(Status.OK).entity("Consultation updated successfully").build();
	}
	
	@Path("getConsultationById")
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConsultationById(@QueryParam(value = "id") int id) {
		List<Consultation> consultations=consService.getConsultationById(id);
		if(consultations.isEmpty()){
			   return (Response.status(Response.Status.NOT_FOUND).entity("No consultations found in database").build());}
		   else 
			   return (Response.status(Response.Status.OK).entity(consultations).build());
		}
	
	
	@GET
	@Path("SearchAllConsultations")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllConsultations() {
	   List<Consultation> consultations=consService.getAllConsultation();
	   if(consultations.isEmpty()){
		   return (Response.status(Response.Status.NOT_FOUND).entity("No consultations found in database").build());}
	   else 
		   return (Response.status(Response.Status.OK).entity(consultations).build());
	}
	
	@GET
	@Path("SearchForPricyConsultations")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPricyConsultations(@QueryParam(value ="price") double price) {
	   List<Consultation> consultations=consService.getPricyConsultation(price);
	   if(consultations.isEmpty()){
		   return (Response.status(Response.Status.NOT_FOUND).entity("No consultations with superior price have been found in database").build());}
	   else 
		   return (Response.status(Response.Status.OK).entity(consultations).build());
	}
	
	@GET
	@Path("SearchForBestConsultations")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBestConsultations() {
	   List<Consultation> consultations=consService.getBestConsultations();
	   if(consultations.isEmpty()){
		   return (Response.status(Response.Status.NOT_FOUND).entity("No consultations with 5 out of 5 rating have been found in database").build());}
	   else 
		   return (Response.status(Response.Status.OK).entity(consultations).build());
	}
	@GET
	@Path("SearchForConsultationsByDoctor")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConsultationByDoctor(@QueryParam(value = "id") int id) {
	   List<Consultation> consultations=consService.getDoctorConsultations(id);
	   if(consultations.isEmpty()){
		   return (Response.status(Response.Status.NOT_FOUND).entity("No consultations").build());}
	   else 
		   return (Response.status(Response.Status.OK).entity(consultations).build());
	}
	
	 @Path("update")
		@PUT
		@PermitAll
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response updateConsultation(Doctor d, @Context SecurityContext securityContext) {
	    	System.out.println("**********************************************");
	    	User u=userServ.findUser(securityContext.getUserPrincipal().getName());
	    	d.setId(u.getId());
	    	System.out.println(d.getCity());
	    	d=(doctorServ.getDoctorById(u.getId()));
	    	doctorServ.update(d);
			return Response.status(Status.OK).entity("Doctor profile updated successfully").build();
		}
}
