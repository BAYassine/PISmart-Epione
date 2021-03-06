package resources;

import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import entities.Appointment;
import entities.Consultation;
import entities.Reason;
import entities.Rating;
import entities.Doctor;
import entities.User;
import interfaces.AppointmentServiceLocal;
import interfaces.UserServiceLocal;

@Path("Appointment")
public class AppointmentResource {
    @EJB
    AppointmentServiceLocal appointmentServ;
    @EJB
    UserServiceLocal userServ;

    @GET
    @PermitAll
    @Path("/{name}")
    @Produces("application/json")
    public Response Appbydoc(@PathParam("name") String name) {
        List<Appointment> lr = appointmentServ.getAppointmentsByDoctorname(name);
        return Response.status(Response.Status.OK).entity(lr).build();
    }

    @GET
    @Path("all")
    @PermitAll
    @Produces("application/json")
    public Response getAllApp() {
        List<Appointment> lr = appointmentServ.getAllAppointments();
        System.out.println("Apppppppppp"+lr);
        return Response.status(Response.Status.OK).entity(lr).build();
    }

	/**
        * Author : Oumayma
     */
    @GET
    @RolesAllowed({"ROLE_PATIENT", "ROLE_DOCTOR"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAppointment(@Context SecurityContext securityContext, @QueryParam(value = "id") int id, @QueryParam(value = "date") String date) throws ParseException {
        User u = userServ.findUser(securityContext.getUserPrincipal().getName());
        List<Appointment> appList = new ArrayList<>();
        Appointment app;
        System.out.println("****************************************" + u.getUsername());
        // Search by Appointment ID
        if (id != 0 && date == null) {
            System.out.println("*****************************************by id only");
            app = appointmentServ.getAppointmentById(id);
            if (app != null)
                return (Response.status(Response.Status.OK).entity(app).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("Appointment not founded").build());
        }
        // Search by Patient/Doctor Id and Date
        else if (id == 0 && date != null) {
            System.out.println("**********************************by y");
            if (u.getRole().equals("ROLE_PATIENT")) {
                appList = appointmentServ.getPatientsAppointmentByDate(date, u.getId());
                if (!appList.isEmpty()) {
                    System.out.println("**********************************appppp");
                    return (Response.status(Response.Status.OK).entity(appList).build());
                }
                return (Response.status(Response.Status.NOT_FOUND).entity("No appointment").build());

            } else if (u.getRole().equals("ROLE_DOCTOR")) {
                appList = appointmentServ.getDoctorsAppointmentByDate(date, u.getId());
                if (!appList.isEmpty())
                    return (Response.status(Response.Status.OK).entity(appList).build());
                return (Response.status(Response.Status.NOT_FOUND).entity("No appointment").build());
            } else
                return (Response.status(Response.Status.BAD_REQUEST).entity("No appointment").build());
        }
        // Search by all appointment of a doctor or a patient
        else if (id == 0 && date == null) {
            System.out.println("**********************************nothing");
            if (u.getRole().equals("ROLE_PATIENT")) {
                appList = appointmentServ.getAppointmentsByPatient(u.getId());
                if (!appList.isEmpty())
                    return (Response.status(Response.Status.OK).entity(appList).build());
                return (Response.status(Response.Status.NOT_FOUND).entity("No appointment").build());

            } else if (u.getRole().equals("ROLE_DOCTOR")) {
                appList = appointmentServ.getAppointmentsByDoctor(u.getId());
                if (!appList.isEmpty())
                    return (Response.status(Response.Status.OK).entity(appList).build());
                return (Response.status(Response.Status.NOT_FOUND).entity("No appointment").build());

            } else
                return (Response.status(Response.Status.BAD_REQUEST).entity("No appointment").build());
        }
        return (Response.status(Response.Status.BAD_REQUEST).entity("No appointment").build());
    }

    /**
     * Author : Oumayma
     */
    @POST
    @RolesAllowed("ROLE_PATIENT")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAppointment(@Context SecurityContext securityContext, Appointment app) throws ParseException {
        if (app != null) {
            User u = userServ.findUser(securityContext.getUserPrincipal().getName());
            int id;
            id = appointmentServ.addAppointment(app, u.getId(), u.getEmail());
            if (id != 0)
                return Response.status(Status.OK).entity("Appointment added").build();
            return Response.status(Status.NOT_ACCEPTABLE).entity("Appointment not added").build();
        } else
            return Response.status(Status.NOT_ACCEPTABLE).entity("Appointment not added").build();
    }

    /**
     * Author : Oumayma
     */
    @PUT
    @RolesAllowed({"ROLE_PATIENT", "ROLE_DOCTOR"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAppointment(@Context SecurityContext securityContext, Appointment app, @QueryParam(value = "idA") int idApp) {
        User u = userServ.findUser(securityContext.getUserPrincipal().getName());
        if (idApp == 0) {
            if (app != null) {
                if (appointmentServ.updateAppointment(app, u.getId()) != 0)
                    return Response.status(Status.OK).entity("Updated").build();
                return Response.status(Status.NOT_ACCEPTABLE).entity("Not Updated").build();
            } else
                return Response.status(Status.NOT_ACCEPTABLE).entity("Not Updated").build();
        } else {
            if (appointmentServ.cancelAppointment(idApp, u.getId()) != 0) {
                return Response.status(Status.OK).entity("Canceled").build();
            }
            return Response.status(Status.NOT_FOUND).entity("can't canceled").build();
        }

    }

    /**
     * Author : Yassine
     */
    @PUT
    @RolesAllowed("ROLE_DOCTOR")
    @Path("/changestate/{id}")
    public Response updateState(@Context SecurityContext securityContext, @PathParam("id") int id,
                                @QueryParam("action") boolean action) {
        System.out.println(action);
        User u = userServ.findUser(securityContext.getUserPrincipal().getName());
        if (!appointmentServ.startEndAppointment(id, action, u.getId()))
            return Response.status(Status.FORBIDDEN).build();
        return Response.status(Status.OK).build();
    }

    /**
     * Author : Oumayma
     */
    @DELETE
    @RolesAllowed("ROLE_PATIENT")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAppointment(@Context SecurityContext securityContext, @QueryParam(value = "idA") int idA) {
        User u = userServ.findUser(securityContext.getUserPrincipal().getName());
        if (idA != 0) {
            appointmentServ.deleteAppointment(idA, u.getId());
            return Response.status(Status.OK).entity("Appointment deleted").build();
        } else
            return Response.status(Status.NOT_ACCEPTABLE).entity("Appontment not deleted").build();
    }


    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Path("AppById/{doctorId}")
    public Response getAppointmentById(@PathParam(value="doctorId")int doctorId) {

        return Response.ok(appointmentServ.getCalendarById(doctorId)).build();


    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("addAPP/{doctorId}")
    public Response addApp(Appointment app,@PathParam(value="doctorId")int doctorId) {


        appointmentServ.addApp(app, doctorId);
        return Response.status(Status.OK).entity("Appointment added").build();


    }


    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("addAPPhh")
    public Response addApp(Appointment app) {


        appointmentServ.addAppHH(app);
        return Response.status(Status.OK).entity("Appointment added").build();


    }

    @GET
    @Path("SearchAllAppointments")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllConsultations() {
        //User u = userServ.findUser(securityContext.getUserPrincipal().getName());
        List<Appointment> apps=appointmentServ.getAllAppointments();
        if(apps.isEmpty()){
            return (Response.status(Response.Status.NOT_FOUND).entity("No apps found in database").build());}
        else
            return (Response.status(Response.Status.OK).entity(apps).build());
    }

    @DELETE
    @Path("Delete")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteConsultation(@QueryParam(value = "id") int id) {
        if (id !=0) {
            appointmentServ.deleteAppointment(id);
            return Response.status(Status.OK).entity("Appointment deleted").build();
        }
        else
            return Response.status(Status.NOT_ACCEPTABLE).entity("Appointment couldn't be deleted").build();
    }




}
