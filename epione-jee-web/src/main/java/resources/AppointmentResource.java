package resources;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import entities.Appointment;

import interfaces.AppointmentServiceLocal;

@Path("Appointment")
public class AppointmentResource {
	@EJB
	AppointmentServiceLocal appointmentServ;
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getAppointmentById(@PathParam(value="id")int id){

		return (Response.status(Response.Status.FOUND).entity(appointmentServ.getAppointmentById(id)).build());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAppointmentByDate(@QueryParam(value="date")String date){
		if (date!=null){
			return (Response.status(Response.Status.FOUND).entity(appointmentServ.getAppointmentByDate(date)).build());	
		}else{
			List<Appointment> list=new ArrayList<Appointment>();
			list= appointmentServ.getAllAppointments();
			return (Response.status(Response.Status.OK).entity(list).build());
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response add(Appointment app)
	{
		if(app!=null)
		{appointmentServ.addAppointment(app,2,1);
		return Response.status(Status.CREATED).entity("ok").build();}
		else
			return Response.status(Status.NOT_ACCEPTABLE).entity("not ok").build();}
	}

