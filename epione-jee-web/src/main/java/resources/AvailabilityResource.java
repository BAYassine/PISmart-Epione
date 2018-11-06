package resources;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import interfaces.AvailabilityServiceLocal;

@Path("Availability")
public class AvailabilityResource {
    @EJB
    AvailabilityServiceLocal availableServ;

    @GET
    @RolesAllowed({"ROLE_PATIENT", "ROLE_DOCTOR"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDoctor(@QueryParam(value = "idD") int idD, @QueryParam(value = "startDate") String startDate) throws ParseException {
        if (startDate != null) {
            if (!availableServ.checkAvailability(idD, startDate).isEmpty())
                return (Response.status(Response.Status.OK).entity("not available").build());
            else
                return (Response.status(Response.Status.OK).entity("available").build());


        }
        return (Response.status(Response.Status.OK).entity(availableServ.checkAvailabilityById(idD)).build());

    }

}
