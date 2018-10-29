package services;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


@Stateless
public class AppointmentService implements AppointmentServiceLocal, AppointmentServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@EJB
	private AvailabilityServiceLocal availServ;

	@EJB
	NotificationAppServiceLocal notificationManager;
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
			notificationManager.addNotification(app);
			return true;
		}
		return false;
		
	}
	public void deleteAppointment(int idA){
		em.remove(em.find(Appointment.class, idA));

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
		java.util.Date d1;

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
        Appointment app = em.find(Appointment.class, idAppointment);
        Consultation cons = em.find(Consultation.class, idConsultaion);
        app.setConsultation(cons);

    }

	@Override
	public List<Appointment> getAppointmentsByPatient(int idPatient) {
		TypedQuery< Appointment> query=em.createQuery("SELECT a FROM Appointment a where a.patient.id= :idPatient",Appointment.class);
		query.setParameter("idPatient", idPatient);
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

    @Override
    public List<Appointment> getAppointmentsByDoctor(int idDoctor) {
        TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a where a.doctor.id= :idDoctor", Appointment.class);
        query.setParameter("idDoctor", idDoctor);
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





	


