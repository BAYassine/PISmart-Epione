package services;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.Doctor;
import entities.Speciality;
import interfaces.SpecialityServiceLocal;
import interfaces.SpecialityServiceRemote;

@Stateless
public class SpecialityService implements SpecialityServiceLocal,SpecialityServiceRemote {
	
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
	@EJB
	SpecialityServiceLocal specService;
	
	public JsonObject addSpeciality(Speciality s) {
		em.persist(s);
		return Json.createObjectBuilder().add("Success", s.getId()).build();
	}
	
	@Override
	public void removeSpeciality(int idS) {
		em.remove(em.find(Speciality.class, idS));
	}
	
	@Override
	public JsonObject chosenSpeciality(int idS, int idDoctor) {
		Doctor doctor = em.find(Doctor.class, idDoctor);
			Speciality speciality = em.find(Speciality.class, idS);
			if(speciality== null){
				return Json.createObjectBuilder().add("id non existant", "id inexistant").build();
			}
			else
			doctor.setSpeciality(speciality);
		
		return Json.createObjectBuilder().add("Doc:", "Doctor have chosen his speciality successfully").build();
	}
	@Override
	public JsonObject getSpecialityById(int idS) {
		Speciality speciality=em.find(Speciality.class, idS);
		return Json.createObjectBuilder().add("Speciality name is", speciality.getName()).build();
	}
	
	@Override
	public JsonObject changeSpecName(String specName, String newspecName) {
		Speciality spec=em.createQuery("select s from Speciality s where s.name = :specName",Speciality.class)
				.setParameter("specName", specName).getSingleResult();
		spec.setName(newspecName);
		em.persist(spec);
		return Json.createObjectBuilder().add("Speciality name is replaced with", spec.getName()).build();
	}
}
