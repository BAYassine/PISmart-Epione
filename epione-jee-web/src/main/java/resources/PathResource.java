package resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import entities.Path;
import entities.Treatment;
import entities.User;
import interfaces.DoctorServiceLocal;
import interfaces.PathServiceLocal;
import interfaces.PatientServiceLocal;
import interfaces.TreatmentServiceLocal;
import interfaces.UserServiceLocal;

@javax.ws.rs.Path ("/paths")

public class PathResource {
	
	@EJB
	PathServiceLocal ps;
	
	@EJB
	TreatmentServiceLocal ts;
	
	@EJB
	UserServiceLocal userServ;
	
	@EJB
	DoctorServiceLocal doctorServ;
	
	@EJB
	PatientServiceLocal patientService;
	
		
	
	@POST
	@RolesAllowed("ROLE_DOCTOR")
	//@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPath(Path p/*, @Context SecurityContext securityContext*/) {
		try {

			/*User u=userServ.findUser(securityContext.getUserPrincipal().getName());
		 	p.setDoctor(doctorServ.getDoctorById(u.getId()));
			System.out.println("******************** Id : "+ u.getId());*/
			System.out.println("tttttttttttttttttttt    "+p.getDescription());
			ps.addPath(p);
			return Response.status(Status.OK).entity(p).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
    }
	
	
	
	@POST
	@javax.ws.rs.Path("/addTreatment")
	@RolesAllowed("ROLE_DOCTOR")
	//@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTreatemntToPath(@QueryParam("idPath") int idPath , Treatment treat ) {
		if((idPath != 0)&&(treat != null)) {
			Path p = ps.getPathById(idPath);
			
			if(treat.getId() == 0 ) {
				
			ts.addTreatment(treat);
			List<Treatment> treats = p.getList_treat();
			treat.setPath(p);
			ts.updateTreatment(treat);
			treats.add(treat);
			p.setList_treat(treats);
			
			}else {
				
				List<Treatment> treats = p.getList_treat();
				treat.setPath(p);
				ts.updateTreatment(treat);
				treats.add(treat);
				p.setList_treat(treats);
				
			}
		return Response.status(Status.CREATED).entity(p).build();
		}else {
			return Response.status(Status.BAD_REQUEST).entity("POST Invalid").build();

		}
	}
	
	
	@PUT
	@RolesAllowed("ROLE_DOCTOR")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePath(Path p) {
		ps.updatePath(p);
		return Response.status(Status.ACCEPTED).entity(p).build();
	}
	
	
	
	@DELETE
	@RolesAllowed("ROLE_DOCTOR")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePath(Path p) {
		
		ps.deletePath(p);
		return Response.status(Status.ACCEPTED).entity("Path Rmoved").build();
	}
	@DELETE
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	@javax.ws.rs.Path("/id")
	public Response deletePathId(@QueryParam("idpath") int idpath) {
		Path p = ps.getPathById(idpath);
		ps.deletePath(p);
		return Response.status(Status.ACCEPTED).entity("Path Rmoved").build();
	}
	


	
	// GETALL | SEARCH by id | by date | by >date | by  <date | by date && desc

			@GET
			@Produces(MediaType.APPLICATION_JSON)
            @PermitAll
               public Response findAllPaths(@QueryParam("id") int id, @QueryParam("date") String date, 
					                      @QueryParam("desc") String desc, @QueryParam("dateComp") String dateComp
					                      ) {
				if(id != 0) {
					
					return Response.status(Status.OK).entity(ps.getPathById(id)).build();
					
				}else if ((desc != null) && (date == null)){
					
					return Response.status(Status.FOUND).entity(ps.getPathsByDesc(desc)).build();

				}else if ((desc == null) && (date != null) && (dateComp == null)){
					
					return Response.status(Status.FOUND).entity(ps.getPathsByDate(convertDate(date))).build();

				}else if ( ((desc != null) && (date != null))) {
					
					return Response.status(Status.FOUND).entity(ps.getPathsByDate(convertDate(date), desc)).build();

				}else if ((date != null)&&(dateComp.equals(">"))) {
					
					return Response.status(Status.FOUND).entity(ps.getPathsDateGreaterThen(convertDate(date))).build();

				}else if ((date != null)&&(dateComp.equals("<"))) {
					
					return Response.status(Status.FOUND).entity(ps.getPathsDateLessThen(convertDate(date))).build();

				}else {
					
					return Response.status(Status.OK).entity(ps.getAllPaths()).build();
				}
				
				
			}
			
			@GET
			@RolesAllowed("ROLE_PATIENT")
			@Produces(MediaType.APPLICATION_JSON)
			@javax.ws.rs.Path("/getPatientConnected")
			public Response getPathsUserConnected(@Context SecurityContext securityContext) {
				User u=userServ.findUser(securityContext.getUserPrincipal().getName());
				
				return  Response.status(Status.ACCEPTED).entity(ps.getPatientsPath(u.getId())).build();
			}
			
			@GET
			@RolesAllowed("ROLE_DOCTOR")
			@Produces(MediaType.APPLICATION_JSON)
			@javax.ws.rs.Path("/getDoctorConnected")
			public Response getPathsDoctorConnected(@Context SecurityContext securityContext) {
				User u=userServ.findUser(securityContext.getUserPrincipal().getName());
				
				return  Response.status(Status.ACCEPTED).entity(ps.getDoctorsPath(u.getId())).build();
			}
			
			@GET
			@PermitAll
			@Produces(MediaType.APPLICATION_JSON)
			@javax.ws.rs.Path("/getAllPatient")
			public Response getAllPatient(@Context SecurityContext securityContext) {
				
				
				return  Response.status(Status.ACCEPTED).entity(patientService.findAll()).build();
			}
			

			
	
	private Date convertDate(String s) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date;
		try {
			date = format.parse(s);
			return date;
		} catch (ParseException e) {
			System.out.println("Convertion date impossible");
			return null;
		}
	}

}
