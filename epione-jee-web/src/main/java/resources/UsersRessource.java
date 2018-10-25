package resources;

import entities.Doctor;
import entities.User;
import interfaces.DoctorServiceLocal;
import interfaces.UserServiceLocal;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("users")
public class UsersRessource {

    @EJB
    private UserServiceLocal userService;

    @POST
    @PermitAll
    @Consumes("application/json")
    @Produces("application/json")
    public Response addDoctor(User d){
        int id = userService.create(d);
        return Response.status(201).entity(id).build();
    }

}
