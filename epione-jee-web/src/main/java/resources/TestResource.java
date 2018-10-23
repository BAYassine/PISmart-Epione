package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class TestResource {
	
	@GET
	public String dummyTest() {
		return "API works";
	}

}
