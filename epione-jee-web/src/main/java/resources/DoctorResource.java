package resources;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import entities.Appointment;
import entities.Doctor;
import entities.Message;
import interfaces.AppointmentServiceLocal;
import interfaces.DoctorServiceLocal;
import interfaces.MessageServiceLocal;
import org.primefaces.json.JSONObject;

@Path("Doctor")
public class DoctorResource {

    @EJB
    private DoctorServiceLocal doctorService;

    @EJB
    private AppointmentServiceLocal appointmentService;

    @EJB
    private MessageServiceLocal messageService;

    /**
     * Author : Oumayma
     */
    @GET
    @RolesAllowed({ "ROLE_PATIENT", "ROLE_DOCTOR" })
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getDoctorById(@PathParam(value = "id") int id) {
        Doctor doc = doctorService.getDoctorById(id);
        if (doc != null)
            return (Response.status(Response.Status.FOUND).entity(doc).build());

        return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the Id").build());
    }

    /**
     * Author : Oumayma
     */
    @GET
    @RolesAllowed({ "ROLE_PATIENT", "ROLE_DOCTOR" })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctor(@QueryParam(value = "idS") int idS, @QueryParam(value = "location") double location,
                              @QueryParam(value = "name") String name) {
        List<Doctor> doc = new ArrayList<>();

        // Search by name
        if ((name != null) && (location == 0.0) && (idS == 0)) {
            doc = doctorService.getDoctorByName(name);
            if (!doc.isEmpty())
                return (Response.status(Response.Status.FOUND).entity(doc).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the name").build());
        }

        // Search by Location
        else if ((name == null) && (location != 0.0) && (idS == 0)) {
            doc = doctorService.getDoctorByLocation(location);
            if (!doc.isEmpty())
                return (Response.status(Response.Status.FOUND).entity(doc).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the Location.").build());
        }

        // Search by Speciality
        else if ((name == null) && (location == 0.0) && (idS != 0)) {
            doc = doctorService.getDoctorBySpeciality(idS);
            if (!doc.isEmpty())
                return (Response.status(Response.Status.FOUND).entity(doc).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the speciality.").build());
        }

        // Search by speciality and location
        else if ((name == null) && (location != 0.0) && (idS != 0)) {
            doc = doctorService.getDoctorBySpecialitAndLocation(idS, location);
            if (!doc.isEmpty())
                return (Response.status(Response.Status.FOUND).entity(doc).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the location and speciality.").build());
        }

        // Search all doctors
        else {
            doc = doctorService.getDoctors();
            if (!doc.isEmpty())
                return (Response.status(Response.Status.FOUND).entity(doc).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("NO doctors").build());
        }
    }

//
//    @GET
//    @RolesAllowed("ROLE_DOCTOR")
//    @Produces("application/json")
//    public Response router(@QueryParam("path") String path,
//                           @QueryParam("from") String since,
//                           @QueryParam("limit") int limit,
//                           @Context SecurityContext securityContext) {
//        switch (path) {
//            case "app_inc":
//                return nextAppointments(securityContext);
//            case "total":
//                if (since != null)
//                    return totalAppointments(securityContext, since);
//                else
//                    return totalAppointments(securityContext, "2018/10/19");
//            case "inbox":
//                if (limit != 0)
//                    return inbox(securityContext, limit);
//                else return inbox(securityContext, 10);
//            default:
//                return dashboard(securityContext);
//        }
//    }
//
//    private Response inbox(SecurityContext securityContext, int limit) {
//        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
//        List<Message> messages = messageService.inbox(doctor, limit);
//        return Response.status(200).entity(messages).build();
//    }
//
//    /**
//     * Author : Yassine
//     */
//    @GET
//    @RolesAllowed("ROLE_DOCTOR")
//    @Produces("application/json")
//    public Response dashboard(@Context SecurityContext securityContext) {
//        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
//        List<Appointment> appointments = appointmentService.upcoming(doctor);
//        JSONObject result = new JSONObject();
//        result.put("doctor", doctor);
//        result.put("appointments upcoming", appointments);
//        result.put("average appointements per day", appointmentService.averageAppointements(doctor));
//        result.put("ongoing", appointmentService.ongoing(doctor) != null ? appointmentService.ongoing(doctor) : "No ongoing appointements");
//        SimpleDateFormat time_formatter = new SimpleDateFormat("H' hours, 'm' minutes and 's' seconds'");
//        time_formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
//        result.put("average duration for consultaions", time_formatter.format(appointmentService.averageDuration(doctor)));
//        result.put("total appointements this week", appointmentService.totalAppointements(doctor, "2018/10/19"));
//        result.put("inbox", messageService.inbox(doctor, 10));
//        return Response.status(200).entity(result).build();
//    }
//
//    /**
//     * Author : Yassine
//     */
//    @GET
//    @Produces("application/json")
//    public Response nextAppointments(@Context SecurityContext securityContext) {
//        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
//        List<Appointment> appointments = appointmentService.upcoming(doctor);
//        return Response.status(200).entity(appointments).build();
//    }
//
//    @GET
//    @Produces("application/json")
//    public Response totalAppointments(@Context SecurityContext securityContext, String since) {
//        Doctor doctor = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
//        long total = appointmentService.totalAppointements(doctor, since);
//        return Response.status(200).entity(total).build();
//    }

    /**
     * Author : Yassine
     */
    @POST
    @PermitAll
    @Produces("application/json")
    @Consumes("application/json")
    public Response addDoctor(Doctor d) {
        int id = doctorService.create(d);
        return Response.status(201).entity(id).build();
    }


}
