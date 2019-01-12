package resources;

import entities.User;
import interfaces.UserServiceLocal;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.simple.JSONObject;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        result.put("New subscribers per month", userService.subscrtionsPerMonth());
        return Response.status(200).entity(result).build();
    }

}
