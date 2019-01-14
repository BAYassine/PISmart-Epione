package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Appointment;
import entities.Consultation;
import entities.Doctor;
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
	public List<Consultation> getConsultationById(int consultationId) {
		TypedQuery< Consultation> query=em.createQuery("SELECT c FROM Consultation c where c.id="+consultationId,Consultation.class);
		 List<Consultation> list=new ArrayList<>();
		 list=query.getResultList();
		return list;
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
	@Override
	public List<Consultation> getDoctorConsultations(int doctor) {
		Doctor doc=em.find(Doctor.class,doctor);
		 TypedQuery<Appointment> query=em.createQuery("SELECT a FROM Appointment a  where a.doctor=:idDoc",Appointment.class).setParameter("idDoc", doc);
		 List<Appointment> list=new ArrayList<>();
		 list=query.getResultList();
		 
		 List<Consultation> listC=new ArrayList<>();
		
		for (int i=0;i<list.size();i++)
		{
			listC.add(list.get(i).getConsultation());
		}

		return listC;
	}

	@Override
	public int addConsultation(Consultation c, int t) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
