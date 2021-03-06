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

		Treatment t = em.find(Treatment.class, treatment.getId());
		em.remove(t);
	}

	@Override
	public List<Treatment> getAllTreatments() {
		TypedQuery<Treatment> query = em.createQuery("SELECT p FROM Treatment p", Treatment.class);
		 System.out.println("reqNumb 1) SELECT t FROM Treatment t");
		 return (List<Treatment>) query.getResultList();
	}

	@Override
	public Treatment getTreatmentById(int id) {
		return 		em.find(Treatment.class, id);
	}
	
	@Override
	public List<Treatment> getTreatmentsByRecomDoc(String treat) {
		 System.out.println("reqNumb 2) SELECT t FROM Treatment t where t.recomended_doc like :treat");
		TypedQuery<Treatment> query = em.createQuery("SELECT t FROM Treatment t where t.recomended_doc like :treat", Treatment.class);
		 return (List<Treatment>) query.setParameter("treat", "%"+treat+"%").getResultList();
	}
	
	@Override
	public List<Treatment> getTreatmentsByDesc(String desc) {
		 System.out.println("reqNumb 3) SELECT t FROM Treatment t where t.description like :desc");
		TypedQuery<Treatment> query = em.createQuery("SELECT t FROM Treatment t where t.description like :desc", Treatment.class);
		 return (List<Treatment>) query.setParameter("desc", "%"+desc+"%").getResultList();
	}
	
	@Override
	public List<Treatment> getTreatmentsByRecomDocAndDesc(String recDoc , String desc) {
		 System.out.println("reqNumb 4) SELECT t FROM Treatment t where t.recomended_doc like :treat and  t.description like :desc ");
		TypedQuery<Treatment> query = em.createQuery("SELECT t FROM Treatment t where t.recomended_doc like :treat and  t.description like :desc ", Treatment.class);
		 return (List<Treatment>) query.setParameter("treat", "%"+recDoc+"%").setParameter("desc", "%"+desc+"%").getResultList();
	}
	
	
	@Override
	public List<Treatment> getPatientsTreatment(int id) {
			System.out.println("req num 5) SELECT p FROM Treatment p WHERE p.patient.id = :id");
			TypedQuery<Treatment> query = em.createQuery("SELECT t FROM Treatment t WHERE t.path.id in (SELECT p.id FROM Path p WHERE p.patient.id = :id )", Treatment.class);
		 return (List<Treatment>) query.setParameter("id", id).getResultList();

	}
	@Override
	public List<Treatment> getDoctorsTreatment(int id) {
			System.out.println("req num 6) SELECT t FROM Treatment t WHERE t.path.id = (SELECT p.id FROM Path WHERE p.doctor.id = :id )");
		 TypedQuery<Treatment> query = em.createQuery("SELECT t FROM Treatment t WHERE t.path.id = (SELECT p.id FROM Path p WHERE p.doctor.id = :id )", Treatment.class);
		 return (List<Treatment>) query.setParameter("id", id).getResultList();

	}
	
	public List<Treatment> getTreatmentsPath(int idPath){
		System.out.println("SELECT t FROM Treatment t WHERE t.path.id = :id");
		TypedQuery<Treatment> query = em.createQuery("SELECT t FROM Treatment t WHERE t.path.id = :id", Treatment.class);
	 return (List<Treatment>) query.setParameter("id", idPath).getResultList();
	}

	public Long countTreatPath(int idPath) {
		System.out.println("SELECT t FROM Treatment t WHERE t.path.id = :id");
		TypedQuery<Long> query = em.createQuery("SELECT count(t) FROM Treatment t WHERE t.path.id = :id", Long.class);
	 return (Long) query.setParameter("id", idPath).getSingleResult();
		
	}
	
}
