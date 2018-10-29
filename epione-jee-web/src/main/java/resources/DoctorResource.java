package resources;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entities.Doctor;
import interfaces.DoctorServiceLocal;

@Path("Doctor")
public class DoctorResource {
	@EJB
	DoctorServiceLocal doctorServ;
	// By Oumayma
	@GET
	@RolesAllowed({ "ROLE_PATIENT", "ROLE_DOCTOR" })
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getAppointmentById(@PathParam(value = "id") int id) {
        Doctor doc=doctorServ.getDoctorById(id);
        if(doc!=null)
		return (Response.status(Response.Status.FOUND).entity(doc).build());
        
        return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the Id").build());
	}


	// By Oumayma
	@GET
	@RolesAllowed({ "ROLE_PATIENT", "ROLE_DOCTOR" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDoctor(@QueryParam(value = "idS") int idS, @QueryParam(value = "location") double location,
			@QueryParam(value = "name") String name) {
        List<Doctor> doc=new ArrayList<>();
        
		// Search by name
		if ((name != null) && (location==0.0) && (idS==0))
			{ doc=doctorServ.getDoctorByName(name);
				if(!doc.isEmpty())   
					return (Response.status(Response.Status.FOUND).entity(doc).build());
				return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the name").build());
			}
		
		// Search by Location
		else if ((name == null) && (location!=0.0) && (idS==0))
			{ doc=doctorServ.getDoctorByLocation(location);
				if(!doc.isEmpty())   
					return (Response.status(Response.Status.FOUND).entity(doc).build());
			    return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the Location.").build());
			}
		
		// Search by Speciality
		else if ((name == null) && (location==0.0) && (idS!=0))
			{ doc=doctorServ.getDoctorBySpeciality(idS);
				if(!doc.isEmpty())   
					return (Response.status(Response.Status.FOUND).entity(doc).build());
				return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the speciality.").build());
			}
		
		// Search by speciality and location
		else if ((name == null) && (location!=0.0) && (idS!=0))
			{ doc=doctorServ.getDoctorBySpecialitAndLocation(idS, location);
				if(!doc.isEmpty())
					return (Response.status(Response.Status.FOUND).entity(doc).build());
				return (Response.status(Response.Status.NOT_FOUND).entity("Doctor not found please verify the location and speciality.").build());
			}
		
		// Search all doctors
		else
			{ doc=doctorServ.getDoctors();
				if(!doc.isEmpty())
					return (Response.status(Response.Status.FOUND).entity(doc).build());
				return (Response.status(Response.Status.NOT_FOUND).entity("NO doctors").build());
			}

	}
}
