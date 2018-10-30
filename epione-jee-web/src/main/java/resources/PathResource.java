package resources;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.security.PermitAll;
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
import entities.Treatment;
import interfaces.PathServiceLocal;
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
	
		
	
	@POST
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPath(Path p) {
		ps.addPath(p);
		return Response.status(Status.CREATED).entity(p).build();
    }
	
	
	
	@POST
	@javax.ws.rs.Path("/addTreatment")
	@PermitAll
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
