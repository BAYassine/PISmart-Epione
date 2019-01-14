package services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;

import entities.*;
import entities.Appointment.states;

import interfaces.AppointmentServiceLocal;
import interfaces.AppointmentServiceRemote;
import interfaces.AvailabilityServiceLocal;
import interfaces.NotificationAppServiceLocal;

@Stateless
public class AppointmentService implements AppointmentServiceLocal, AppointmentServiceRemote {

    @PersistenceContext(unitName = "epione-jee-ejb")
    EntityManager em;

    @EJB
    private AvailabilityServiceLocal availServ;

    @EJB
    NotificationAppServiceLocal notificationManager;
    @EJB
    private NotificationAppServiceLocal notifServ;

    /**
     * Author : Oumayma
     */
    public int addAppointment(Appointment app, int idPatient, String emailPatient) throws ParseException {
        List<Availability> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateS = format.format(app.getDate_start());
        list = availServ.checkAvailability(app.getDoctor().getId(), dateS);
        Patient pat = em.find(Patient.class, idPatient);
        if (list.isEmpty()) {
            app.setPatient(pat);
            em.persist(app);
            EmailService email = new EmailService();
            String subject, content;
            subject = "Medical Appointment confirmation";
            content = "Your appointment on : " + app.getDate_start() + " has been confirmed. ";
            email.sendEmail(subject, content, emailPatient);
            NotificationApp n = new NotificationApp(new Date(), pat, "Your appointment has been confirmed.");
            notifServ.sendNotifToPatient(idPatient, n);
            return app.getId();
        }
        return 0;
    }

    /**
     * Author : Oumayma
     */
    public int cancelAppointment(int appId, int idP) {

        Appointment app = em.find(Appointment.class, appId);
        if (app != null && app.getPatient().getId() == idP) {
            app.setState(states.CANCELED);
            notificationManager.addNotification(app);
            return app.getId();
        }
        return 0;

    }

    public void deleteAppointment(int idA, int idP) {
        Appointment app = em.find(Appointment.class, idA);
        if (app.getPatient().getId() == idP)
            em.remove(app);
    }


    /**
     * Author : Oumayma
     */
    @Override
    public int updateAppointment(Appointment app, int idR) {
        Reason r = em.find(Reason.class, idR);
        app.setReason(r);
        em.merge(app);
        return app.getId();
    }

    /**
     * Author : Oumayma
     */
    public Appointment getAppointmentById(int appointmentId) {
        return (em.find(Appointment.class, appointmentId));

    }

    @Override
    public List<Appointment> getAppointmentByDate(String dateapp) throws ParseException {
        java.util.Date d1 = null;
        System.out.println("1/ SELECT a FROM Appointment a WHERE a.date_start = :dateapp");
        d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateapp);
        java.sql.Date d = new java.sql.Date(d1.getTime());
        System.out.println(d1);
        return em.createQuery("SELECT a FROM Appointment a WHERE a.date_start = :dateapp", Appointment.class).setParameter("dateapp", d1).getResultList();
    }

    /**
     * Author : Oumayma
     */
    public List<Appointment> getAllAppointments() {
        TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a", Appointment.class);
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
        TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a where a.patient.id= :idPatient", Appointment.class);
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
    public List<Appointment> getAppointmentsByDoctorname(String name) {
        TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a where a.doctor.username= :name", Appointment.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
    /**
     * Author : Oumayma
     */
    public List<Appointment> getDoctorsAppointmentByDate(String date, int idD) throws ParseException {
        java.util.Date d1 = null;
        d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        java.sql.Date d = new java.sql.Date(d1.getTime());
        java.sql.Date tomorrow = new java.sql.Date(d.getTime() + 24 * 60 * 60 * 1000);
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE a.date_start BETWEEN :dateapp AND :tomorrow AND a.doctor.id = :idD", Appointment.class);
        query.setParameter("dateapp", d);
        query.setParameter("tomorrow", tomorrow);
        query.setParameter("idD", idD);
        return query.getResultList();
    }

    /**
     * Author : Oumayma
     */
    public List<Appointment> getPatientsAppointmentByDate(String date, int idP) throws ParseException {
        Date d1 = null;
        d1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        java.sql.Date d = new java.sql.Date(d1.getTime());
        java.sql.Date tomorrow = new java.sql.Date(d.getTime() + 24 * 60 * 60 * 1000);
        System.out.println("daaaaaate" + tomorrow.toString());
        Query query = em.createQuery("SELECT a FROM Appointment a WHERE a.date_start BETWEEN :dateapp AND :tomorrow AND a.patient.id = :idP", Appointment.class);
        query.setParameter("dateapp", d);
        query.setParameter("tomorrow", tomorrow);
        query.setParameter("idP", idP);
        System.out.println(idP);
        return query.getResultList();
    }

    /**
     * Author : Yassine
     */
    public List<Appointment> upcoming(Doctor doctor) {
        String sql = "SELECT a from Appointment a WHERE a.doctor = :doctor "
                + "AND a.state = :state AND date(a.date_start) = date(current_date)";
        Query query = em.createQuery(sql).setParameter("doctor", doctor).setParameter("state", Appointment.states.UPCOMING);
        return query.getResultList();
    }

    /**
     * Author : Yassine
     */
    public Appointment ongoing(Doctor doctor) {
        String sql = "SELECT a from Appointment a where a.state = :state and a.doctor = :doctor "
                + "order by a.date_start ASC ";
        TypedQuery<Appointment> query = em.createQuery(sql, Appointment.class)
                .setParameter("state", Appointment.states.ONGOING)
                .setParameter("doctor", doctor);
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
    public long totalAppointements(Doctor doctor, String since) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        try {
            date = formatter.parse(since);
        } catch (ParseException | NullPointerException e) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, -(6 - c.get(Calendar.DAY_OF_WEEK)));
            date = c.getTime();
        }
        String sql = "SELECT count(a) from Appointment a where a.date_start > :from and a.date_start < current_date and a.doctor = :doctor";
        Query query = em.createQuery(sql).setParameter("from", date).setParameter("doctor", doctor);
        return (long) query.getSingleResult();
    }

    /**
     * Author : Yassine
     */
    public Map<String, Long> appointmentPerDay(Doctor doctor, String since) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-d");
        Date date = new Date();
        try {
            date = formatter.parse(since);
        } catch (ParseException | NullPointerException e) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, -7);
            date = c.getTime();
        }
        String sql = "SELECT count(a), date(a.date_start) from Appointment a " +
                "where a.date_start > :date and a.date_start < current_date and a.doctor = :doctor " +
                "GROUP BY date(a.date_start)";
        Query query = em.createQuery(sql).setParameter("doctor", doctor).setParameter("date", date);
        List<Object[]> list = query.getResultList();
        Map<String, Long> map = new HashMap<>();
        list.forEach(k -> {
            map.put(formatter.format((Date) k[1]), (Long) k[0]);
        });
        return map;
    }

    public Map<String, Long> appointmentPerMonth(Doctor doctor,String since) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        try {
            date = formatter.parse(since);
        } catch (ParseException | NullPointerException e) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, -7);
            date = c.getTime();
        }
        String sql = "SELECT count(*) total, DATE_FORMAT(date_start, '%Y-%m') month " +
                "FROM appointment " +
                "WHERE doctor_id = :doctor_id AND state = 3 AND date_start > :date " +
                "GROUP BY month";
        Query query = em.createNativeQuery(sql).setParameter("doctor_id", doctor.getId()).setParameter("date", date);
        List<Object[]> list = query.getResultList();
        Map<String, Long> map = new HashMap<>();
        list.forEach(k -> map.put((String) k[1], ((BigInteger) k[0]).longValue()));
        return map;
    }

    public Map<String, Long> appointmentPerYear(Doctor doctor,String since) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = new Date();
        try {
            date = formatter.parse(since);
        } catch (ParseException | NullPointerException e) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.YEAR, -7);
            date = c.getTime();
        }
        String sql = "SELECT count(*) total, DATE_FORMAT(date_start, '%Y') year " +
                "FROM appointment " +
                "WHERE doctor_id = :doctor_id AND state = 3 AND date_start > :date " +
                "GROUP BY year";
        Query query = em.createNativeQuery(sql).setParameter("doctor_id", doctor.getId()).setParameter("date", date);
        List<Object[]> list = query.getResultList();
        Map<String, Long> map = new HashMap<>();
        list.forEach(k -> map.put((String) k[1], ((BigInteger) k[0]).longValue()));
        return map;
    }

    /**
     * Author : Yassine
     */
    public Date averageDuration(Doctor doctor) {
        String sql = "SELECT SUM(duration)/ COUNT(*) as average FROM ( " +
                "SELECT TIME_TO_SEC(TIMEDIFF(date_end, date_start)) as duration, TIME(date_start), TIME(date_end) FROM appointment " +
                "WHERE doctor_id = :doctor AND state = :state AND date_end IS NOT NULL " +
                ") AS durations";
        Query query = em.createNativeQuery(sql).setParameter("doctor", doctor.getId()).setParameter("state", Appointment.states.DONE.ordinal());
        BigDecimal avg = (BigDecimal) query.getSingleResult();
        return new Date(avg.longValue()*1000);
    }

    /**
     * Author : Yassine
     */
    public Long totalPatient(Doctor doctor){
        String sql = "SELECT count(distinct a.patient) from Appointment a where a.doctor = :doctor";
        Query q = em.createQuery(sql).setParameter("doctor", doctor);
        return (Long) q.getSingleResult();
    }

    /**
     * Author : Yassine
     */
    public boolean startEndAppointment(int id, boolean action, int uid){
        Appointment app = em.find(Appointment.class, id);
        if(app.getDoctor().getId() != uid)
            return false;
        if(action){
            app.setState(states.ONGOING);
            app.setDate_start(new Date());
        }
        else {
            app.setState(states.DONE);
            app.setDate_end(new Date());
        }
        em.merge(app);
        return true;
    }




}