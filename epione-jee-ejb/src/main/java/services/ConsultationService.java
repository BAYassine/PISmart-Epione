package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Appointment;
import entities.Consultation;
import interfaces.ConsultationServiceLocal;
import interfaces.ConsultationServiceRemote;

@Stateless
public class ConsultationService implements ConsultationServiceLocal , ConsultationServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@Override
	public int addConsultation(Consultation c) {
		em.persist(c);
		return c.getId();
	}

	@Override
	public void deleteConsultation(int consultationId) {
		em.remove(em.find(Consultation.class, consultationId));
		
	}

	@Override
	public int updateConsultation(Consultation c) {
		em.merge(c);
		return c.getId();
	}

	@Override
	public Consultation getConsultationById(int consultationId) {
		return (em.find(Consultation.class, consultationId));
	}

	@Override
	public List<Consultation> getAllConsultation() {
		 TypedQuery< Consultation> query=em.createQuery("SELECT c FROM Consultation c",Consultation.class);
		 List<Consultation> list=new ArrayList<>();
		 list=query.getResultList();
		return list;
	}
	
	@Override
	public List<Consultation> getPricyConsultation(double price) {
		 TypedQuery< Consultation> query=em.createQuery("SELECT c FROM Consultation c where c.price >=:price",Consultation.class);
		return query.setParameter("price", price).getResultList();
	}
	
	@Override
	public List<Consultation> getBestConsultations() {
		 TypedQuery< Consultation> query=em.createQuery("SELECT c FROM Consultation c where c.rating=5",Consultation.class);
		 List<Consultation> list=new ArrayList<>();
		 list=query.getResultList();
		return list;
	}
	
}
