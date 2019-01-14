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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import entities.Report;
import entities.User;
import interfaces.ReportServiceLocal;
import interfaces.UserServiceLocal;

@Path("/reports")
public class ReportResource {
	
	@EJB
	ReportServiceLocal rs ;

	@EJB
	UserServiceLocal userServ;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response addReport(Report report) {
		rs.addReport(report);
		return Response.status(Status.OK).entity(report).build();
		
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_DOCTOR")
	public Response updateReport(Report report) {
		rs.updateReport(report);
		return Response.status(Status.FOUND).entity(report).build();
		
	}
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("ROLE_DOCTOR")
	public Response deleteReport(Report report) {
		rs.deleteReport(report);
		return Response.status(Status.FOUND).entity("Report Removed").build();
		
	}
	
	// GETALL | SEARCH by id | by date | by >date | by  <date | by date && content

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response findAllReport(@QueryParam("id") int id, @QueryParam("date") String date, 
			                      @QueryParam("content") String content, @QueryParam("dateComp") String dateComp
			                      ) {
		
		if(id != 0) {
			
			return Response.status(Status.OK).entity(rs.getReportById(id)).build();
			
		}else if ((content != null) && (date == null)){
			
			return Response.status(Status.OK).entity(rs.getReportByContent(content)).build();

		}else if ((content == null) && (date != null) && (dateComp == null)){
			
			return Response.status(Status.OK).entity(rs.getReportByDate(convertDate(date))).build();

		}else if ( ((content != null) && (date != null))) {
			
			return Response.status(Status.OK).entity(rs.getReportByDateContent(convertDate(date), content)).build();

		}else if ((date != null)&&(dateComp.equals(">"))) {
			
			return Response.status(Status.OK).entity(rs.getReportDateGreaterThen(convertDate(date))).build();

		}else if ((date != null)&&(dateComp.equals("<"))) {
			
			return Response.status(Status.OK).entity(rs.getReportDateLessThen(convertDate(date))).build();

		}else {
			
			return Response.status(Status.OK).entity(rs.getAllReports()).build();
		}
		
		
	}
	
	@GET
	@RolesAllowed("ROLE_PATIENT")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getPatientConnected")
	public Response getReportsUserConnected(@Context SecurityContext securityContext) {
		User u=userServ.findUser(securityContext.getUserPrincipal().getName());
		
		return  Response.status(Status.ACCEPTED).entity(rs.getPatientsReport(u.getId())).build();
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
