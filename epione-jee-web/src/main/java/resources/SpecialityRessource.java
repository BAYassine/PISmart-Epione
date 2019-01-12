package resources;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import entities.Speciality;
import entities.User;
import interfaces.SpecialityServiceLocal;
import interfaces.UserServiceLocal;
/*Fares*/
@Path("Speciality")
public class SpecialityRessource {
	@EJB
	SpecialityServiceLocal specService;
	
	@EJB
	UserServiceLocal userServ;
	@POST
	@Path("SpecialityAdding")
	@RolesAllowed("ROLE_DOCTOR")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addReason(Speciality s) {
		if (s != null) {
			specService.addSpeciality(s);
			return Response.status(Status.CREATED).entity("Speciality added successfully").build();
		} 
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Speciality could not be added").build();
	}
	
	@DELETE
	@Path("Delete")
	@RolesAllowed("ROLE_DOCTOR")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response DeleteReason(@QueryParam(value = "idS") int idS) {
		if (idS !=0) {
			specService.removeSpeciality(idS);
			return Response.status(Status.OK).entity("Speciality deleted from database").build();
		} 
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Speciality could not be deleted").build();
	}
	
	@Path("SpecialityChoice")
	@POST
	@RolesAllowed("ROLE_DOCTOR")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response chosenSpeciality(@QueryParam(value ="idS")int idS,@Context SecurityContext securityContext) {
		User u = userServ.findUser(securityContext.getUserPrincipal().getName());
		return Response.ok(specService.chosenSpeciality(idS, u.getId())).build();
	}
	
	@Path("getSpecialityById")
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecialityById(@QueryParam(value = "idS") int idS) {
		return Response.ok(specService.getSpecialityById(idS)).build();
	}
	@Path("updateSpecialityName")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newSpecName(@QueryParam(value="name") String name,@QueryParam(value="newName") String newName) {
		return Response.ok(specService.changeSpecName(name, newName)).build();
	}
	@Path("getSpecialities")
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecialities() {
		return Response.ok(specService.getAllSpecialities()).build();
	}
		
}
