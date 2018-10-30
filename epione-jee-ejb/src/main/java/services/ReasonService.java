package services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Doctor;
import entities.Reason;
import entities.Speciality;
import interfaces.ReasonServiceLocal;
import interfaces.ReasonServiceRemote;
@Stateless
public class ReasonService implements ReasonServiceLocal,ReasonServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
	@Override
	public int addReason(Reason r, int idSpeciality) {
		Speciality s=em.find(Speciality.class, idSpeciality);
		r.setSpeciality(s);
		em.persist(r);
		return r.getId();
	}

	@Override
	public int updateReason(Reason r,int idSpeciality) {
		Speciality s=em.find(Speciality.class, idSpeciality);
		r.setSpeciality(s);
		em.merge(r);
		return r.getId();
	}

	@Override
	public List<Reason> searchReasonBySpeciality(int idSpeciality) {
		 TypedQuery< Reason> query=em.createQuery("SELECT r FROM Reason r where r.speciality.id= :idSpeciality",Reason.class);
		 query.setParameter("idSpeciality", idSpeciality);
			return query.getResultList();
	}
	
	
	/*Fares*/


	/*@Override
	public int updateReason(Reason r,int idSpeciality) {
		Speciality s=em.find(Speciality.class, idSpeciality);
		r.setSpeciality(s);
		em.merge(r);
		return r.getId();
	}*/

	

	
	@Override
	public JsonObject addReason(Reason r) {
		em.persist(r);
		return Json.createObjectBuilder().add("Success", r.getId()).build();
	}


	/*@Override
	public List<Reason> searchReasonByDoctor(int idDoctor) {
		 TypedQuery< Reason> query=em.createQuery("SELECT r FROM Reason r where r.doctor.id= :idDoctor",Reason.class);
		 query.setParameter("idDoctor", idDoctor);
			return query.getResultList();
	}*/

	@Override
	public void removeReason(int idR) {
		em.remove(em.find(Reason.class, idR));
	}

	@Override
	public Set<Reason> searchReasonByDoctor(int idDoctor) {
		Doctor doctor= em.find(Doctor.class, idDoctor);
		return doctor.getReasons();
	}
	
	@Override
	public JsonObject chosenReasons(List<Integer> idsReasons, int idDoctor) {
		Doctor doctor = em.find(Doctor.class, idDoctor);
		for (Integer idReasons : idsReasons) {
			Reason reason = em.find(Reason.class, idReasons);
			if(reason== null){
				return Json.createObjectBuilder().add("id non existant", "id inexistant").build();
			}
			else
			doctor.getReasons().add(reason);
		}
		return Json.createObjectBuilder().add("success", "reason added to doctor").build();
	}
	
	public JsonObject newReasons(List<Integer> idsReasons, int idDoctor) {
		Doctor doctor = em.find(Doctor.class, idDoctor);
		Set<Reason> reasons= new HashSet<>();
		for (Integer idReasons : idsReasons) {
			Reason reason = em.find(Reason.class, idReasons);
			if(reason== null){
				return Json.createObjectBuilder().add("id non existant", "id inexistant").build();
			}
			else
			reasons.add(reason);
		}
		doctor.setReasons(reasons);
		return Json.createObjectBuilder().add("success", "reasons modified").build();
	}
	
	@Override
	public JsonObject getReasonById(int idReason) {
		Reason reason=em.find(Reason.class, idReason);
		return Json.createObjectBuilder().add("Reason name is", reason.getName()).build();
	}
	
	@Override
	public JsonObject getReasonByName(String nameReason, String updatedReasonName) {
		Reason reason=em.createQuery("select r from Reason r where r.name = :nameReason",Reason.class)
				.setParameter("nameReason", nameReason).getSingleResult();
		reason.setName(updatedReasonName);
		em.persist(reason);
		return Json.createObjectBuilder().add("Reason name is replaced with", reason.getName()).build();
	}


}
