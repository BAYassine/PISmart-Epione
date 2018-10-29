package resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import entities.Path;
import interfaces.PathServiceLocal;

@javax.ws.rs.Path ("/paths")

public class PathResource {
	
	@EJB
	PathServiceLocal ps;
	
		
	
	@POST
	@RolesAllowed("ROLE_ADMIN")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPath(Path p) {
		ps.addPath(p);
		return Response.status(Status.CREATED).entity(p).build();
	}
	
	@PUT
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePath(Path p) {
		ps.updatePath(p);
		return Response.status(Status.ACCEPTED).entity(p).build();
	}
	
	
	//à tester...
	
	@DELETE
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePath(Path p) {
		
		ps.deletePath(p);
		return Response.status(Status.ACCEPTED).entity("Path Rmoved").build();
	}
	
	@GET
	@javax.ws.rs.Path("/test")
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response findDoctor(@QueryParam("path") int id) {
		return Response.status(Status.FOUND).entity(ps.getPathDoctor(id)).build();
	}
	
	
	// GETALL | SEARCH by id | by date | by >date | by  <date | by date && desc

			@GET
			@Produces(MediaType.APPLICATION_JSON)
			@PermitAll
			public Response findAllPaths(@QueryParam("id") int id, @QueryParam("date") String date, 
					                      @QueryParam("desc") String desc, @QueryParam("dateComp") String dateComp
					                      ) {
				
				if(id != 0) {
					
					return Response.status(Status.FOUND).entity(ps.getPathById(id)).build();
					
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
					
					return Response.status(Status.FOUND).entity(ps.getAllPaths()).build();
				}
				
				
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
