package services;

import entities.*;
import entities.Appointment.states;
import interfaces.AppointmentServiceLocal;
import interfaces.AppointmentServiceRemote;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Stateless
public class AppointmentService implements AppointmentServiceLocal, AppointmentServiceRemote {
    @PersistenceContext(unitName = "epione-jee-ejb")
    EntityManager em;

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
    public boolean cancelAppointment(int appId) {

        Appointment app = em.find(Appointment.class, appId);
        if (app != null) {
            app.setState(states.CANCELED);
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
        Date d1 = null;

        d1 = new SimpleDateFormat("yyyy-MM-dd").parse(dateapp);

        return em.createQuery("SELECT a FROM Appointment a WHERE a.date_start = :dateapp", Appointment.class).setParameter("dateapp", d1).getResultList();
    }

    @Override
    public List<Appointment> getAllAppointments() {
        TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a", Appointment.class);
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





	


