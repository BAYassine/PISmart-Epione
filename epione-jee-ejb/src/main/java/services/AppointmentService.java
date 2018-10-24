package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Appointment;
import entities.Consultation;
import entities.Doctor;
import entities.Patient;
import interfaces.AppointmentServiceLocal;
import interfaces.AppointmentServiceRemote;


@Stateless
public class AppointmentService implements AppointmentServiceLocal, AppointmentServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@Override
	public int addAppointment(Appointment app, int idDoctor, int idPatient) {
			Doctor doc=em.find(Doctor.class,idDoctor);
			app.setDoctor(doc);
			Patient pat=em.find(Patient.class,idPatient);
			app.setPatient(pat);
			em.persist(app);
			return app.getId();
		}

	@Override
	public void cancelAppointment(int appId) {
		Appointment app=em.find(Appointment.class, appId);
		app.setCanceled(true);
		
	}

	@Override
	public int updateAppointment(Appointment app) {
		em.merge(app);
		return app.getId();
	}

	@Override
	public Appointment getAppointmentById(int appointmentId) {
		return (em.find(Appointment.class, appointmentId));
	
	}

	@Override
	public List<Appointment> getAllAppointments() {
		 TypedQuery< Appointment> query=em.createQuery("SELECT a FROM Appointment a",Appointment.class);
		 List<Appointment> list=new ArrayList<>();
		 list=query.getResultList();
		return list;
	}

	@Override
	public void affectConsultation(int idAppointment, int idConsultaion) {
		Appointment app=em.find(Appointment.class, idAppointment);
		Consultation cons=em.find(Consultation.class, idConsultaion);
		app.setConsultation(cons);
		
	}
	


	}





	


