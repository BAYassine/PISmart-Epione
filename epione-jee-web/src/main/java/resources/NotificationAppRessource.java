package resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import entities.Appointment;
import entities.NotificationApp;
import entities.Patient;
import entities.Rating;
import entities.User;
import interfaces.NotificationAppServiceLocal;
import interfaces.UserServiceLocal;
import services.UserService;

@Path("/notification")
public class NotificationAppRessource {
	
	@EJB
	NotificationAppServiceLocal notifManager;
	@EJB
	UserServiceLocal usersManager;
	
	@POST
	@RolesAllowed({ "ROLE_PATIENT", "ROLE_DOCTOR" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response addNotification(@Context SecurityContext securityContext){
		User u = usersManager.findUser(securityContext.getUserPrincipal().getName());
		//notifManager.addNotification(Appointment app,);
		return Response.status(Status.CREATED).entity("Notification Envoyée :").build();
	}
	
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificationByDate(@QueryParam(value="date")String date){
		if (date!=null){
			return (Response.status(Response.Status.FOUND).entity(notifManager.getNotificationByDate(date)).build());	
		}else{
			List<NotificationApp> list=new ArrayList<NotificationApp>();
			list= notifManager.getAllNotification();
			return (Response.status(Response.Status.OK).entity(list).build());
		}
	}
	
	@GET
	@Path("confirmation")
	@RolesAllowed({ "ROLE_PATIENT"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response ConfirmationRDV(@Context SecurityContext securityContext){
		User u = usersManager.findUser(securityContext.getUserPrincipal().getName());
		return Response.status(Status.OK).entity(notifManager.Confirmation(u.getId())).build();
	}
	/** Author: oumayma
	 * **/
	@GET
	@Path("show")
	@RolesAllowed({ "ROLE_PATIENT"})
	@Produces(MediaType.APPLICATION_JSON)
	public Response showMyNotif(@Context SecurityContext securityContext){
		User u = usersManager.findUser(securityContext.getUserPrincipal().getName());
		return Response.status(Status.OK).entity(notifManager.getNotficationByPatient(u.getId())).build();
	}
}
