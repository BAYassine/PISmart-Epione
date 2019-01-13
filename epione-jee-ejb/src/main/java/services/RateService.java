package services;


import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.fasterxml.jackson.core.Versioned;

import entities.Appointment;
import entities.Patient;
import entities.Rating;
import interfaces.RateServiceLocal;


@Stateless
public class RateService implements RateServiceLocal {

	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@Override
	public JsonObject AddRate(Rating r,int AppId) {
		/* verify the rate */
		if(r.getRate()<0 || r.getRate()>5)
			return Json.createObjectBuilder().add("Failed", "Rate Value must be between 1 and 5").build();
		em.persist(r);
		r.setCreated_at(new Date());
		Appointment app = em.find(Appointment.class, AppId);
		System.out.println(app.getId());
		r.setAppointment(app);
		return Json.createObjectBuilder().add("Success", "Rate saved successfully").build();
	}

	@Override
	public Rating getRateByAppoitement(int AppId) {
		TypedQuery<Rating> query = em.createQuery("SELECT r FROM Rating r WHERE r.appointment.id ="+AppId, Rating.class);
		return query.getSingleResult();
	}

	@Override
	public List<Rating> getAllRates() {
		TypedQuery<Rating> query = em.createQuery("SELECT r FROM Rating r", Rating.class);
		return query.getResultList();
	}

	@Override
	public JsonObject EditRate(Rating r) {
		em.createQuery("update Rating r SET r.rate = "+r.getRate()+" WHERE r.id = "+r.getId()).executeUpdate();
		return Json.createObjectBuilder().add("Success", "Rate Updated successfully").build();
	}

	@Override
	public JsonObject deleteRate(int id,int idP) {
		Rating r = em.find(Rating.class,id);
		if(r!=null){
			TypedQuery<Integer> query = em.createQuery("SELECT r.appointment.patient.id FROM Rating r WHERE r.id = "+id, Integer.class);
			int verifP =  query.getSingleResult();
			System.out.println(verifP+"azeaez"+idP+""+query.getResultList());
			if(idP==verifP){
				em.createQuery("delete from Rating r WHERE r.id = "+id).executeUpdate();
				return Json.createObjectBuilder().add("Success", "Rate Deleted").build();
			}
			else{if(idP!=verifP){
				return Json.createObjectBuilder().add("Failed", "Action refused").build();
			}
			else return Json.createObjectBuilder().add("Oups", "Somthing Wrong").build();
				}
		}
		else{
			return Json.createObjectBuilder().add("Oups", "Entity Not Found").build();}


	}

	@Override
	public List<Rating> getRatesByPatient(String paientUserName) {
		TypedQuery<Rating> query = em.createQuery(
				"SELECT r FROM Rating r where r.appointment.patient.username =:paientUserName", Rating.class);
		return query.setParameter("paientUserName", paientUserName).getResultList();
	}

	@Override
	public List<Rating> getRatesByDoctor(String doctorUserName) {
		TypedQuery<Rating> query = em.createQuery(
				"SELECT r FROM Rating r where r.appointment.doctor.username =:doctorUserName", Rating.class);
		return query.setParameter("doctorUserName", doctorUserName).getResultList();
	}

	@Override
	public double DoctorRate(int idDoctor) {
		double TotalRate = 0;
		/* count number of appointment for the doctor */
		TypedQuery<Long> nbrDoc = em.createQuery(
				"SELECT count(rate) FROM Rating r where r.appointment.doctor.id ="+idDoctor, Long.class);
		
		/* rate total for the doctor */
		TypedQuery<Integer> rates = em.createQuery(
				"SELECT rate FROM Rating r where r.appointment.doctor.id ="+idDoctor, Integer.class);
		
		List<Integer> r = rates.getResultList();
		Iterator<Integer> iter =  r.iterator();
		
		while ( iter.hasNext())
			TotalRate += iter.next();
		double avg = (TotalRate/nbrDoc.getSingleResult());
		
		return avg;
	}
	 @Override
	 public List<Rating> getAllRate(){
			TypedQuery<Rating> req = em.createQuery("SELECT r FROM Rating r", Rating.class);
			List<Rating> res = req.getResultList();
			System.out.println("****************** List des Rating ******************");
			System.out.println(res);
			return res;
	 }
	 
	 @Override
	 public List<Appointment> getAppbyuser(String username){
		 TypedQuery<Appointment> query = em.createQuery(
					"SELECT r FROM Appointment r where r.patient.username =:username", Appointment.class);
			return query.setParameter("username", "sbouiamine").getResultList();
	 }
	

}
