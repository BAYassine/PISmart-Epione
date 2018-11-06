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
    @Produces(MediaType.APPLICATION_JSON)
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
    public Response getDoctor(@QueryParam(value = "idS") int idS, @QueryParam(value = "lat") double lat,
    						  @QueryParam(value = "lon") double lon,
    						  @QueryParam(value = "city") String city,
                              @QueryParam(value = "name") String name) {
        List<Doctor> doc = new ArrayList<>();

        // Search by name
        if ((name != null) && (lat == 0.0) && (lon == 0.0)&& (city == null) && (idS == 0)) {
            doc = doctorService.getDoctorByName(name);
            if (!doc.isEmpty())
                return (Response.status(Response.Status.FOUND).entity(doc).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the name").build());
        }

        // Search by Location
        else if ((name == null) && (lat != 0.0) && (lon != 0.0)&& (city == null) && (idS == 0)){
            doc = doctorService.getDoctorByLocation(lat,lon);
            if (!doc.isEmpty())
                return (Response.status(Response.Status.FOUND).entity(doc).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the Location.").build());
        }

        // Search by Speciality
        else if ((name == null) && (lat == 0.0) && (lon == 0.0)&& (city == null) && (idS != 0)) {
            doc = doctorService.getDoctorBySpeciality(idS);
            if (!doc.isEmpty())
                return (Response.status(Response.Status.FOUND).entity(doc).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the speciality.").build());
        }

        // Search by speciality and location
        else if ((name == null) && (lat != 0.0) && (lon != 0.0)&& (city == null) && (idS != 0)) {
            doc = doctorService.getDoctorBySpecialitAndLocation(idS, lat,lon);
            if (!doc.isEmpty())
                return (Response.status(Response.Status.FOUND).entity(doc).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the location and speciality.").build());
        }
       // search by city
        else if ((name == null) && (lat == 0.0) && (lon == 0.0)&& (city != null) && (idS == 0)) {
            doc = doctorService.getDoctorByCity(city);
            if (!doc.isEmpty())
                return (Response.status(Response.Status.FOUND).entity(doc).build());
            return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the location and speciality.").build());
        }
        //search by name and city
        else if ((name != null) && (lat == 0.0) && (lon == 0.0)&& (city != null) && (idS == 0)) {
            doc = doctorService.getDoctorByNameAndCity(name, city);
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
