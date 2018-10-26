package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Path;
import entities.Treatment;
import interfaces.TreatmentServiceLocal;
import interfaces.TreatmentServiceRemote;

@Stateless
public class TreatmentService implements TreatmentServiceLocal, TreatmentServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@Override
	public int addTreatment(Treatment treatment) {
		em.persist(treatment);
		return treatment.getId();
	}

	@Override
	public int updateTreatment(Treatment treatment) {
		Treatment t  = em.find(Treatment.class, treatment.getId());
		t.setAppointment(treatment.getAppointment());
		t.setDescription(treatment.getDescription());
		t.setPath(treatment.getPath());
		t.setRecomended_doc(treatment.getRecomended_doc());
		return treatment.getId();
	}

	@Override
	public void deleteTreatment(Treatment treatment) {

		em.remove(treatment);
	}

	@Override
	public List<Treatment> getAllTreatments() {
		TypedQuery<Treatment> query = em.createQuery("SELECT p FROM Path p", Treatment.class);
		 System.out.println("SELECT t FROM Treatment t");
		 return (List<Treatment>) query.getResultList();
	}

	@Override
	public Treatment getTreatmentById(int id) {
		return 		em.find(Treatment.class, id);
	}

	
}
