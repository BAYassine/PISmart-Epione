package resources;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
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

import entities.Appointment;
import entities.User;
import interfaces.AppointmentServiceLocal;
import interfaces.UserServiceLocal;
import services.UserService;

@Path("Appointment")
public class AppointmentResource {
	@EJB
	AppointmentServiceLocal appointmentServ;

	@EJB
	UserServiceLocal usersManager;
	
	@GET
	@RolesAllowed({ "ROLE_PATIENT", "ROLE_DOCTOR" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAppointmentById(@QueryParam(value = "id") int id,@QueryParam(value = "idD") int idD,@QueryParam(value = "idP") int idP,@QueryParam(value = "date") String date) throws ParseException {
		List<Appointment> appList;
		Appointment app;
		if(id!=0 && idP==0 && idD==0 && date==null){
		app=appointmentServ.getAppointmentById(id);
			if(app!=null)
				return (Response.status(Response.Status.FOUND).entity(app).build());
			return (Response.status(Response.Status.NOT_FOUND).entity("Appointment not founded").build());
      }
      else if(id==0 && idP!=0 && idD==0 &&date==null){
  			appList=appointmentServ.getAppointmentsByPatient(idP);
  			if(!appList.isEmpty())
  				return (Response.status(Response.Status.FOUND).entity(appList).build());
  			return (Response.status(Response.Status.NOT_FOUND).entity("No appointment").build());
       }
      else if(id==0 && idP==0 && idD!=0 &&date==null){
    		appList=appointmentServ.getAppointmentsByDoctor(idD);
    		if(!appList.isEmpty())
     		return (Response.status(Response.Status.FOUND).entity(appList).build());
            
            return (Response.status(Response.Status.NOT_FOUND).entity("No appointment").build());
           }
      else if(id==0 && idP==0 && idD==0 &&date!=null){
  		appList=appointmentServ.getAppointmentByDate(date);
  		if(!appList.isEmpty())
   		return (Response.status(Response.Status.FOUND).entity(appList).build());
          
          return (Response.status(Response.Status.NOT_FOUND).entity("No appointment").build());
         }
      else
    	  return (Response.status(Response.Status.OK).entity(appointmentServ.getAllAppointments()).build());
    	  
	}


	@POST
	@Path("{idD}/{idP}/{idR}")
	@RolesAllowed("ROLE_PATIENT")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addAppointment(Appointment app,@PathParam(value = "idD") int idD,@PathParam(value = "idP") int idP,@PathParam(value = "idR") int idR) {
		if (app != null) {
			appointmentServ.addAppointment(app, idD, idP,idR);
			return Response.status(Status.CREATED).entity("Appointment added").build();	
		} 
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("Appointment not added").build();
	}
	
	@PUT
	@RolesAllowed("ROLE_PATIENT")
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateAppointment(@Context SecurityContext securityContext,Appointment app,@QueryParam(value="idR") int idR,@QueryParam(value="idA")int idApp){
		User u = usersManager.findUser(securityContext.getUserPrincipal().getName());
		if(idApp==0){
			if(app!=null)
				{appointmentServ.updateAppointment(app,idR);
					return Response.status(Status.OK).entity("Updated").build();
				} 
				else
					return Response.status(Status.NOT_ACCEPTABLE).entity("Not Updated").build();
				}
		else
			{
			System.out.println("idAPP"+idApp+" ID USER"+u.getId());
				if (appointmentServ.cancelAppointment(idApp,u.getId())){
					System.out.println("Hey hey"+idApp+" aeza"+u.getId());
					return Response.status(Status.OK).entity("Canceled").build();}

				return Response.status(Status.NOT_FOUND).entity("can't canceled").build();
			}

	
		
}

}
