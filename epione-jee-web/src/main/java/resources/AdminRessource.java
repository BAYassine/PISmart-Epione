package resources;

import interfaces.UserServiceLocal;
import org.json.simple.JSONObject;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("admin")
public class AdminRessource {

    @EJB
    private UserServiceLocal userService;

    @GET
    @Produces("application/json")
    @RolesAllowed("ROLE_ADMIN")
    public Response dashboard(){
        long connectedUsers = userService.todayUsers();
        JSONObject result = new JSONObject();
        result.put("Users connected today", connectedUsers);
        result.put("Registred users this week", userService.registeredSince("22/10/2018"));
        return Response.status(200).entity(result).build();
    }

}
