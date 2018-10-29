package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Appointment;
import entities.Consultation;
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

	@Override
	public void removeReason(int idR) {
		em.remove(em.find(Reason.class, idR));
	}

}
