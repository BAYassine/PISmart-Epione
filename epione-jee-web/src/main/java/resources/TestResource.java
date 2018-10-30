package resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class TestResource {
	
	@GET
	@RolesAllowed("ROLE_ADMIN")
	public String dummyTest() {
		return "API works";
	}

}
