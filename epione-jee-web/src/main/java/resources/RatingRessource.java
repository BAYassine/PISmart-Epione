package resources;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import entities.Rating;
import interfaces.RateServiceLocal;


	
@Path("/rating")
public class RatingRessource {
	
	@EJB
	RateServiceLocal rateManager;
	
	
	@POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response addRate(Rating r){
		System.out.println(r.toString());
		rateManager.AddRate(r);
		return Response.status(Status.CREATED).entity("Rate :"+r.getId()).build();
	}
	
	
	@GET
    @PermitAll
	@Produces("application/json")
	public Response getAllRate() {
		List<Rating> lr = rateManager.getAllRate();
		return Response.status(Response.Status.FOUND).entity(lr).build();
	}

}
