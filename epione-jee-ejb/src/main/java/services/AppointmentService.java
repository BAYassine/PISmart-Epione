package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.resource.spi.work.SecurityContext;

import entities.Appointment;
import entities.Appointment.states;
import entities.Availability;
import entities.Consultation;
import entities.Doctor;
import entities.Patient;
import entities.Reason;
import interfaces.AppointmentServiceLocal;
import interfaces.AppointmentServiceRemote;
import interfaces.AvailabilityServiceLocal;


@Stateless
public class AppointmentService implements AppointmentServiceLocal, AppointmentServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
	
	@EJB
	private AvailabilityServiceLocal availServ;

	@Override
	public int addAppointment(Appointment app, int idPatient) throws ParseException {
		
			System.out.println("id patient user: "+idPatient);
			List<Availability> list=new ArrayList<>();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateS=format.format(app.getDate_start());
			System.out.println("dateee: "+dateS);
			list=availServ.checkAvailability(app.getDoctor().getId(), dateS);
			
			Patient pat=em.find(Patient.class, idPatient);
			if(list.isEmpty()){
				app.setPatient(pat);
				em.persist(app);
				EmailService email=new EmailService();
				email.sendEmail(app.getDate_start());
				return app.getId();
			}
			return 0;
			
		}

	@Override
	public boolean cancelAppointment(int appId,int idP) {
		
		Appointment app=em.find(Appointment.class, appId);
		if(app!=null && app.getPatient().getId()==idP){
			app.setState(states.CANCELED);
			return true;	
		}
		return false;
		
	}
	public void deleteAppointment(int idA){
		em.remove(em.find(Appointment.class, idA));
		
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
	public List<Appointment> getAppointmentByDate(String dateapp) throws ParseException {
		
		
		
		java.util.Date d1=null;
System.out.println("1/ SELECT a FROM Appointment a WHERE a.date_start = :dateapp");
			d1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateapp);
			java.sql.Date d=new java.sql.Date(d1.getTime());
	System.out.println(d1);
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
	
	private Date convertDate(String s) {
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
		Date date;
		try {
			date = format.parse(s);
			return date;
		} catch (ParseException e) {
			System.out.println("Convertion date impossible");
			return null;
		}
	}


	}





	


