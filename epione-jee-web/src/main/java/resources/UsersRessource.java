package resources;

import entities.Doctor;
import entities.Patient;
import entities.User;
import interfaces.DoctorServiceLocal;
import interfaces.PatientServiceLocal;
import interfaces.UserServiceLocal;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Date;

@Path("users")
public class UsersRessource {

    @EJB
    private UserServiceLocal userService;
    @EJB
    private PatientServiceLocal patientService;
    @EJB
    private DoctorServiceLocal doctorService;

    @POST
    @PermitAll
    @Consumes("application/json")
    public Response register(User d){
        d.setPassword(BCrypt.hashpw(d.getPassword(), BCrypt.gensalt()));
        d.setLast_login(new Date());
        userService.create(d);
        return Response.status(201).entity("Thank you for joining us").build();
    }

    @PUT
    @RolesAllowed("ROLE_PATIENT")
    @Consumes("application/json")
    public Response editProfilePatient(@Context SecurityContext securityContext, Patient patient){
        Patient p = patientService.findPatient(securityContext.getUserPrincipal().getName());
        patient.copy(p);
        patient.setId(p.getId());
        patientService.update(patient);
        return Response.status(200).entity("Profile updated successfully").build();
    }

    @PUT
    @RolesAllowed("ROLE_DOCTOR")
    @Consumes("application/json")
    public Response editProfileDoctor(@Context SecurityContext securityContext, Doctor doctor){
        System.out.println(securityContext.getUserPrincipal().getName());
        Doctor d = doctorService.findDoctor(securityContext.getUserPrincipal().getName());
        doctor.copy(d);
        doctor.setId(d.getId());
        doctorService.update(doctor);
        return Response.status(200).entity("Profile updated successfully").build();
    }

    @DELETE
    @RolesAllowed({"ROLE_DOCTOR", "ROLE_PATIENT"})
    public Response deleteAccount(@Context SecurityContext securityContext){
        User u = userService.findUser(securityContext.getUserPrincipal().getName());
        userService.remove(u);
        return Response.status(200).entity("We're sorry to see you go").build();
    }

}
