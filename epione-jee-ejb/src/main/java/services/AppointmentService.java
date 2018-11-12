package services;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.New;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import entities.Appointment;
import entities.Appointment.states;
import entities.Consultation;
import entities.Doctor;
import entities.Patient;
import entities.Reason;
import interfaces.AppointmentServiceLocal;
import interfaces.AppointmentServiceRemote;
import interfaces.NotificationAppServiceLocal;


@Stateless
public class AppointmentService implements AppointmentServiceLocal, AppointmentServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@EJB
	NotificationAppServiceLocal notificationManager;
	@Override
	public int addAppointment(Appointment app, int idDoctor, int idPatient, int idReason) {
			Doctor doc=em.find(Doctor.class,idDoctor);
			app.setDoctor(doc);
			Patient pat=em.find(Patient.class,idPatient);
			app.setPatient(pat);
			Reason r=em.find(Reason.class, idReason);
			app.setReason(r);
			em.persist(app);
			return app.getId();
		}

	@Override
	public boolean cancelAppointment(int appId, int idP) {
		Appointment app=em.find(Appointment.class, appId);
		if(app!=null && app.getPatient().getId()==idP){
			app.setState(states.CANCELED);
			notificationManager.addNotification(app);
			return true;	
		}
		return false;
		
		
	}

	@Override
	public int updateAppointment(Appointment app, int idR) {
		Reason r=em.find(Reason.class, idR);
		app.setReason(r);
		em.merge(app);
		return app.getId();
	}

	@Override
	public Appointment getAppointmentById(int appointmentId) {
		return (em.find(Appointment.class, appointmentId));
	
	}
	@Override
	public List<Appointment> getAppointmentByDate(String dateapp) throws ParseException {
		java.util.Date d1=null;

			d1=new SimpleDateFormat("yyyy-MM-dd").parse(dateapp);
	
		return em.createQuery("SELECT a FROM Appointment a WHERE a.date_start = :dateapp",Appointment.class).setParameter("dateapp",d1).getResultList();
	}
	
	@Override
	public List<Appointment> getAllAppointments() {
		 TypedQuery< Appointment> query=em.createQuery("SELECT a FROM Appointment a",Appointment.class);
		return query.getResultList();
	}

	@Override
	public void affectConsultation(int idAppointment, int idConsultaion) {
		Appointment app=em.find(Appointment.class, idAppointment);
		Consultation cons=em.find(Consultation.class, idConsultaion);
		app.setConsultation(cons);
		
	}

	@Override
	public List<Appointment> getAppointmentsByPatient(int idPatient) {
		TypedQuery< Appointment> query=em.createQuery("SELECT a FROM Appointment a where a.patient.id= :idPatient",Appointment.class);
		query.setParameter("idPatient", idPatient);
		return query.getResultList();
	}

	@Override
	public List<Appointment> getAppointmentsByDoctor(int idDoctor) {
		TypedQuery< Appointment> query=em.createQuery("SELECT a FROM Appointment a where a.doctor.id= :idDoctor",Appointment.class);
		query.setParameter("idDoctor", idDoctor);
		return query.getResultList();
	}
	


	}





	


