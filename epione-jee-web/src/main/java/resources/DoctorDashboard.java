package resources;

import entities.Appointment;
import entities.Doctor;
import entities.Message;
import interfaces.AppointmentServiceLocal;
import interfaces.DoctorServiceLocal;
import interfaces.MessageServiceLocal;
import org.json.simple.JSONObject;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.SimpleDateFormat;
import java.util.List;
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
    public Response router(@QueryParam("path") String path,
                           @QueryParam("from") String since,
                           @QueryParam("limit") int limit,
                           @Context SecurityContext securityContext) {
        if(path == null)
            return dashboard(securityContext);
        switch (path) {
            case "app_inc":
                return nextAppointments(securityContext);
            case "total":
                if (since != null)
                    return totalAppointments(securityContext, since);
                else
                    return totalAppointments(securityContext, "2018/10/19");
            case "inbox":
                if (limit != 0)
                    return inbox(securityContext, limit);
                else return inbox(securityContext, 10);
            default:
                return dashboard(securityContext);
        }
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
        result.put("doctor", doctor);
        result.put("appointments upcoming", appointments);
        result.put("average appointements per day", appointmentService.averageAppointements(doctor));
//        result.put("appointment per day",appointmentService.appointmentPerDay(doctor));
        result.put("ongoing", appointmentService.ongoing(doctor) != null ? appointmentService.ongoing(doctor) : "No ongoing appointements");
        SimpleDateFormat time_formatter = new SimpleDateFormat("H' hours, 'm' minutes and 's' seconds'");
        time_formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        result.put("average duration for consultaions", time_formatter.format(appointmentService.averageDuration(doctor)));
        result.put("total appointements this week", appointmentService.totalAppointements(doctor, "2018/10/19"));
        result.put("inbox", messageService.inbox(doctor, 10));
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


}
