package resources;

import entities.Appointment;
import entities.Doctor;
import entities.Message;
import interfaces.AppointmentServiceLocal;
import interfaces.DoctorServiceLocal;
import interfaces.MessageServiceLocal;
import org.jboss.resteasy.annotations.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Path("/dashboard")
public class DoctorDashboard {

    @EJB
    private DoctorServiceLocal doctorService;
    @EJB
    private MessageServiceLocal messageService;
    @EJB
    private AppointmentServiceLocal appointmentService;

    @GET
    @RolesAllowed("ROLE_DOCTOR")
    @Produces("application/json")
    public Response router(@QueryParam("graph") String graph,
                           @QueryParam("value") String value,
                           @QueryParam("since") String since,
                           @QueryParam("limit") int limit,
                           @Context SecurityContext securityContext) {
            if(value != null)
                    return totalAppointments(securityContext, since);
            if(graph != null){
                if(graph.equals("month"))
                    return monthAppointements(securityContext, since);
                if(graph.equals("day"))
                    return dayAppointements(securityContext, since);
                if(graph.equals("year"))
                    return yearAppointements(securityContext, since);
            }
            if(limit != 0)
                return inbox(securityContext, limit);
            return dashboard(securityContext);
    }


    /**
     * Author : Yassine
     */
    @RolesAllowed("ROLE_DOCTOR")
    @Produces("application/json")
    public Response inbox(SecurityContext securityContext, int limit) {
        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
        List<Message> messages = messageService.inbox(doctor, limit);
        return Response.status(200).entity(messages).build();
    }

    /**
     * Author : Yassine
     */
    @RolesAllowed("ROLE_DOCTOR")
    @Produces("application/json")
    public Response dashboard(@Context SecurityContext securityContext) {
        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
        List<Appointment> appointments = appointmentService.upcoming(doctor);
        JSONObject result = new JSONObject();
        result.put("appointments upcoming", appointments);
        result.put("average appointements per day", appointmentService.averageAppointements(doctor));
        result.put("appointment per day",appointmentService.appointmentPerMonth(doctor, null));
        result.put("ongoing", appointmentService.ongoing(doctor) != null ? appointmentService.ongoing(doctor) : appointments.size() > 0 ? appointments.get(0) : null);
        SimpleDateFormat time_formatter = new SimpleDateFormat("H'h' m'm' s's'");
        time_formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        result.put("average duration for consultations", time_formatter.format(appointmentService.averageDuration(doctor)));
        result.put("total appointments this week", appointmentService.totalAppointements(doctor, null));
        result.put("inbox", messageService.inbox(doctor, 10));
        result.put("unread messages", messageService.unreadMessages(doctor));
        return Response.status(200).entity(result).build();
    }

    /**
     * Author : Yassine
     */
    @RolesAllowed("ROLE_DOCTOR")
    @Produces("application/json")
    public Response nextAppointments(@Context SecurityContext securityContext) {
        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
        List<Appointment> appointments = appointmentService.upcoming(doctor);
        return Response.status(200).entity(appointments).build();
    }

    @RolesAllowed("ROLE_DOCTOR")
    @Produces("application/json")
    public Response totalAppointments(@Context SecurityContext securityContext, String since) {
        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
        long total = appointmentService.totalAppointements(doctor, since);
        return Response.status(200).entity(total).build();
    }

    @RolesAllowed("ROLE_DOCTOR")
    @Produces("application/json")
    public Response dayAppointements(@Context SecurityContext securityContext, String since) {
        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
        return Response.status(200).entity(appointmentService.appointmentPerDay(doctor, since)).build();
    }

    @RolesAllowed("ROLE_DOCTOR")
    @Produces("application/json")
    public Response monthAppointements(@Context SecurityContext securityContext, String since) {
        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
        return Response.status(200).entity(appointmentService.appointmentPerMonth(doctor, since)).build();
    }

    @RolesAllowed("ROLE_DOCTOR")
    @Produces("application/json")
    public Response yearAppointements(@Context SecurityContext securityContext, String since) {
        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
        return Response.status(200).entity(appointmentService.appointmentPerYear(doctor,since)).build();
    }

}
