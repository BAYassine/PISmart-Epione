package resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import entities.Doctolib;
import entities.DoctolibDoctor;
import entities.DoctolibOther;
import interfaces.DoctolibServiceLocal;

@Stateless
@Path("/doctolib")
public class DoctolibResource {
	
	@EJB
	DoctolibServiceLocal DoctolibService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getDoctors(@QueryParam(value="path")String path,@QueryParam(value="name")String name ,@QueryParam(value="speciality")String speciality,@DefaultValue("france") @QueryParam(value="location")String location,@DefaultValue("-1") @QueryParam(value="page") String page){
		
		if(path != null){
			
			if(path.contains("clinique") || path.contains("hopital") || path.contains("centre") || path.contains("cabinet") || path.contains("etablissement") || path.contains("maison")){
				
				DoctolibOther other = new DoctolibOther();
				
				try {
					other = DoctolibService.getOtherByPath(path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				return Response.status(Status.OK).entity(other).build();
				
			}else{
			
				DoctolibDoctor doc = new DoctolibDoctor();
			
				try {
					doc = DoctolibService.getDoctorByPath(path);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				return Response.status(Status.OK).entity(doc).build();
			
			}
		}else{
			
			if(name != null){
				List<Doctolib> lst = new ArrayList<Doctolib>();
				
				try {
					lst = DoctolibService.getListDoctorsByNameAndLocation(name,location,page);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				return Response.status(Status.OK).entity(lst).build();
			}else{
		
				List<Doctolib> lst = new ArrayList<Doctolib>();
		
				try {
					lst = DoctolibService.getListDoctorsBySpecialityAndLocation(speciality,location,page);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				return Response.status(Status.OK).entity(lst).build();
			}
		
		}
		
	}

}
