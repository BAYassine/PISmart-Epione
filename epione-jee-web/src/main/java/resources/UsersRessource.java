package resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Doctor;
import entities.Patient;
import entities.User;
import interfaces.DoctorServiceLocal;
import interfaces.PatientServiceLocal;
import interfaces.UserServiceLocal;
import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Map;

@Path("users")
public class UsersRessource {

    @EJB
    private UserServiceLocal userService;
    @EJB
    private PatientServiceLocal patientService;
    @EJB
    private DoctorServiceLocal doctorService;

    @POST
    @Path("/register")
    @PermitAll
    @Consumes("application/json")
    public Response register(User u){
        u.setPassword(BCrypt.hashpw(u.getPassword(), BCrypt.gensalt()));
        u.setUsername(u.getUsername().toLowerCase());
        u.setEmail(u.getEmail().toLowerCase());
        try {
            if(u.getRole().equals(User.Roles.ROLE_DOCTOR.toString())){
                Doctor d = new Doctor(u);
                doctorService.create(d);
            }
            else if(u.getRole().equals(User.Roles.ROLE_PATIENT.toString())){
                Patient p = new Patient(u);
                patientService.create(p);
            }
            else userService.create(u);
        }catch (EJBException e){
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Email or username already used").build();
        }
        return Response.status(201).entity("Thank you for joining us").build();
    }

    @GET
    @RolesAllowed({"ROLE_PATIENT", "ROLE_DOCTOR"})
    @Produces("application/json")
    public Response showInfos(@Context SecurityContext securityContext){
        User u = userService.findUser(securityContext.getUserPrincipal().getName());
        if(u.getRole().equals(User.Roles.ROLE_DOCTOR.toString())){
            Doctor d = doctorService.findDoctor(u.getUsername());
            return Response.status(200).entity(d).build();
        }
        else if(u.getRole().equals(User.Roles.ROLE_PATIENT.toString())){
            Patient p = patientService.findPatient(u.getUsername());
            return Response.status(200).entity(p).build();
        }
        return Response.status(200).entity(u).build();
    }

    @PUT
    @RolesAllowed({"ROLE_PATIENT", "ROLE_DOCTOR"})
    @Consumes("application/json")
    public Response editProfile(@Context SecurityContext securityContext, Map<String, Object> json){
        Gson gs = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        JSONObject user = new JSONObject(json);
        User u = userService.findUser((String) user.get("username"));
        if (!(u.getId() == (int)user.get("id")))
            return Response.status(401).entity("Access denied").build();
        if(user.get("role").equals(User.Roles.ROLE_DOCTOR.toString())){
            Doctor d = gs.fromJson(user.toString(), Doctor.class);
            doctorService.update(d);
        }
        else if (user.get("role").equals(User.Roles.ROLE_PATIENT.toString())){
            Patient p = gs.fromJson(user.toString(), Patient.class);
            patientService.update(p);
        }
        return Response.status(200).entity("Profile updated successfully").build();
    }

    @DELETE
    @RolesAllowed({"ROLE_DOCTOR", "ROLE_PATIENT"})
    public Response deleteAccount(@Context SecurityContext securityContext){
        User u = userService.findUser(securityContext.getUserPrincipal().getName());
        userService.remove(u);
        return Response.status(200).entity("We're sorry to see you go").build();
    }
    
    @POST
    @PermitAll
    @Path("/check")
    @Produces("application/json")
    public Response check(@QueryParam("username")String username, @QueryParam("password")String password){
       
       User u= userService.check(username, password);
        return Response.status(200).entity(u).build();
    }
}
