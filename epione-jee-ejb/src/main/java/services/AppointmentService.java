package services;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

	 /**
     * Author : Oumayma
     */
	public int addAppointment(Appointment app, int idPatient,String emailPatient) throws ParseException {
			List<Availability> list=new ArrayList<>();
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateS=format.format(app.getDate_start());
			list=availServ.checkAvailability(app.getDoctor().getId(), dateS);
			Patient pat=em.find(Patient.class, idPatient);
			if(list.isEmpty()){
				app.setPatient(pat);
				em.persist(app);
				EmailService email=new EmailService();
				String subject,content;
				subject="Medical Appointment confirmation";
				content="Your appointment on : "+app.getDate_start()+" has been confirmed. ";
				email.sendEmail(subject,content,emailPatient);
				return app.getId();
			}
			return 0;
			
		}

	 /**
     * Author : Oumayma
     */
	public boolean cancelAppointment(int appId,int idP) {
		Appointment app=em.find(Appointment.class, appId);
		if(app!=null && app.getPatient().getId()==idP){
			app.setState(states.CANCELED);
			return true;	
		}
		return false;
		
	}
	public void deleteAppointment(int idA,int idP){
		Appointment app=em.find(Appointment.class, idA);
		if(app.getPatient().getId()==idP)
			em.remove(app);
		
	}

	 /**
     * Author : Oumayma
     */
	public int updateAppointment(Appointment app,int idP) {
		if(app.getPatient().getId()==idP){
			em.merge(app);
			return app.getId();
		}
		return 0;
	}

	 /**
     * Author : Oumayma
     */
	public Appointment getAppointmentById(int appointmentId) {
		return (em.find(Appointment.class, appointmentId));
	
	}
	
	 /**
     * Author : Oumayma
     */
	public List<Appointment> getAllAppointments() {
		 TypedQuery< Appointment> query=em.createQuery("SELECT a FROM Appointment a",Appointment.class);
		return query.getResultList();
	}

	 /**
     * Author : Oumayma
     */
    public void affectConsultation(int idAppointment, int idConsultaion) {
        Appointment app = em.find(Appointment.class, idAppointment);
        Consultation cons = em.find(Consultation.class, idConsultaion);
        app.setConsultation(cons);

    }

    /**
     * Author : Oumayma
     */
	public List<Appointment> getAppointmentsByPatient(int idPatient) {
		TypedQuery< Appointment> query=em.createQuery("SELECT a FROM Appointment a where a.patient.id= :idPatient",Appointment.class);
		query.setParameter("idPatient", idPatient);
		return query.getResultList();
	}

	
	 /**
     * Author : Oumayma
     */
    public List<Appointment> getAppointmentsByDoctor(int idDoctor) {
        TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a where a.doctor.id= :idDoctor", Appointment.class);
        query.setParameter("idDoctor", idDoctor);
        return query.getResultList();
    }
    /**
     * Author : Oumayma
     */
	public List<Appointment> getDoctorsAppointmentByDate(String date,int idD) throws ParseException {
		  java.util.Date d1=null;
			d1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
			java.sql.Date d=new java.sql.Date(d1.getTime());
			java.sql.Date tomorrow= new java.sql.Date( d.getTime() + 24*60*60*1000);
			Query query=em.createQuery("SELECT a FROM Appointment a WHERE a.date_start BETWEEN :dateapp AND :tomorrow AND a.doctor.id = :idD",Appointment.class);
			query.setParameter("dateapp",d);
			query.setParameter("tomorrow", tomorrow);
			query.setParameter("idD",idD);
			return query.getResultList();
			}

	 /**
     * Author : Oumayma
     */
	public List<Appointment> getPatientsAppointmentByDate(String date,int idP) throws ParseException {
				  java.util.Date d1=null;
				d1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
				java.sql.Date d=new java.sql.Date(d1.getTime());
				java.sql.Date tomorrow= new java.sql.Date( d.getTime() + 24*60*60*1000);
				System.out.println("daaaaaate"+tomorrow.toString());
				Query query=em.createQuery("SELECT a FROM Appointment a WHERE a.date_start BETWEEN :dateapp AND :tomorrow AND a.patient.id = :idP",Appointment.class);
				query.setParameter("dateapp",d);
				query.setParameter("tomorrow", tomorrow);
				query.setParameter("idP",idP);
				System.out.println(idP);
				return query.getResultList();
	}
    /**
     * Author : Yassine
     */
    public List<Appointment> upcoming(Doctor doctor) {
        String sql = "SELECT a,d from Appointment a JOIN a.doctor d WHERE d.id = :id_doctor "
                + "AND a.date_start > CURRENT_DATE ";
        Query query = em.createQuery(sql).setParameter("id_doctor", doctor.getId());
        return query.getResultList();
    }

    /**
     * Author : Yassine
     */
    public Appointment ongoing(Doctor doctor) {
        String sql = "SELECT a from Appointment a where a.state = :state "
                + "order by a.date_start ASC ";
        TypedQuery<Appointment> query = em.createQuery(sql, Appointment.class).setParameter("state", Appointment.states.ONGOING);
        Appointment a;
        try {
            a = query.setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            a = null;
        }
        return a;
    }

    /**
     * Author : Yassine
     */
    public double averageAppointements(Doctor doctor) {
        String sql = "SELECT count(a) from Appointment a where a.doctor = :doctor group by cast(a.date_start as date)";
        Query query = em.createQuery(sql).setParameter("doctor", doctor);
        List<Long> appCnt = query.getResultList();
        double sum = appCnt.stream().mapToLong(i -> i).sum();
        return sum / appCnt.size();
    }

    /**
     * Author : Yassine
     */
    public long totalAppointements(Doctor doctor, String from) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = formatter.parse(from);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sql = "SELECT count(a) from Appointment a where a.date_start > :from and a.date_start < current_date and a.doctor = :doctor";
        Query query = em.createQuery(sql).setParameter("from", date).setParameter("doctor", doctor);
        return (long) query.getSingleResult();
    }

    /**
     * Author : Yassine
     */
    public Date averageDuration(Doctor doctor) {
        String sql = "SELECT SUM(duration)/ COUNT(*) as average FROM ( " +
                "SELECT TIME_TO_SEC(TIMEDIFF(date_end, date_start)) as duration, TIME(date_start), TIME(date_end) FROM appointment " +
                "WHERE doctor_id = :doctor AND state = :state " +
                ") AS durations";
        Query query = em.createNativeQuery(sql).setParameter("doctor", doctor.getId()).setParameter("state", Appointment.states.DONE.ordinal());
        BigDecimal avg = (BigDecimal) query.getSingleResult();
        return new Date(avg.longValue() * 1000);
    }



}





	


