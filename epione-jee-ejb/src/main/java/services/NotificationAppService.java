package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Appointment;
import entities.Doctor;
import entities.NotificationApp;
import entities.NotificationApp.Confiramtion;
import entities.Patient;
import entities.User;
import interfaces.NotificationAppServiceRemote;
import interfaces.NotificationAppServiceLocal;

@Stateless
public class NotificationAppService implements NotificationAppServiceLocal, NotificationAppServiceRemote {

    @PersistenceContext(unitName = "epione-jee-ejb")
    EntityManager em;

	@Override
	public JsonObject addNotification(Appointment app) {
		System.out.println("App"+app.toString());
		System.out.println("App"+app.getDate_start().getYear());
		TypedQuery<Appointment> queryPa = em
				.createQuery("SELECT n FROM Appointment n WHERE DAY(n.date_start) = :dateS ORDER BY n.date_start DESC", Appointment.class)
				.setParameter("dateS", app.getDate_start().getDate());
		queryPa.setMaxResults(1);
		List<Appointment> DernierPatient = queryPa.getResultList();
		System.out.println("Les appointement de lannée"+DernierPatient);
		TypedQuery<NotificationApp> query = em
				.createQuery("SELECT n FROM NotificationApp n WHERE n.patientnotif = :idp and n.new_Appointement_Date = :dateS", NotificationApp.class)
				.setParameter("idp", app.getPatient())
				.setParameter("dateS", app.getDate_start());
		List<NotificationApp> liste = query.getResultList();
		System.out.println("Nottt"+liste);
		if(liste.isEmpty()){
		NotificationApp na = new NotificationApp();
		na.setNotified_at(new Date());
		na.setNew_Appointement_Date(app.getDate_start());
		na.setPatientnotif(DernierPatient.get(0).getPatient());
		na.setConfirmation(Confiramtion.WAITING);
		na.toString();
		em.persist(na);
		System.out.println("Notification envoyé");
		return Json.createObjectBuilder().add("Success", "Notification Envoyé").add("id", na.toString()).build();
		}
		else {
			System.out.println("Vous étes Déja Notifié");
			return Json.createObjectBuilder().add("Failed", "Vous étes Déja notifié").build();}
	}

    @Override
    public List<NotificationApp> getNotificationByDate(String date) {
        java.util.Date d1 = null;
        try {
            d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TypedQuery<NotificationApp> query = em
                .createQuery("SELECT n FROM NotificationApp n WHERE n.notified_at = :date", NotificationApp.class)
                .setParameter("date", d1);
        List<NotificationApp> liste = query.getResultList();
        return liste;
    }

    @Override
    public List<NotificationApp> getAllNotification() {
        TypedQuery<NotificationApp> query = em.createQuery("SELECT a FROM NotificationApp a", NotificationApp.class);
        return query.getResultList();
    }

    @Override
    public JsonObject Confirmation(int idP) {
        Patient pat = em.find(Patient.class, idP);
        TypedQuery<NotificationApp> query = em
                .createQuery("SELECT n FROM NotificationApp n WHERE n.patientnotif = :idP", NotificationApp.class)
                .setParameter("idP", pat);
        NotificationApp na = query.getSingleResult();
        TypedQuery<Appointment> querya = em
                .createQuery("SELECT n FROM Appointment n WHERE n.patient = :idP", Appointment.class)
                .setParameter("idP", pat);
        Appointment a = querya.getSingleResult();
        Date confirmedat= new Date();
        System.out.println("cooooo"+confirmedat);
        System.out.println(confirmedat.getHours()-na.getNotified_at().getHours());
        if (na.getConfirmation().equals(Confiramtion.YES)) {
            return Json.createObjectBuilder().add("Ooups !", "Vous avez Deja Change le RDV").build();
        } else if (na.getConfirmation().equals(Confiramtion.WAITING) &&
                na.getNotified_at().getDay()==na.getNew_Appointement_Date().getDay() &&
                na.getNotified_at().getDate()==na.getNew_Appointement_Date().getDate() &&
                na.getNotified_at().getMonth()==na.getNew_Appointement_Date().getMonth() &&
                na.getNotified_at().getYear()==na.getNew_Appointement_Date().getYear()&&
                confirmedat.getHours()-na.getNotified_at().getHours()<2) {
            a.setDate_start(na.getNew_Appointement_Date());
            em.persist(a);
            em.flush();
            return Json.createObjectBuilder().add("Success !", "Confirmation Effectue").build();
        } else
            return Json.createObjectBuilder().add("Ooups !", "Vous avez Deja Annule le changement dhoraire").build();
    }


    @Override
    public int sendNotifToPatient(int idP, NotificationApp n) {
        if (n != null) {
            Patient p = em.find(Patient.class, idP);
            n.setPatientnotif(p);
            em.persist(n);
            return n.getId();
        }
        return 0;
    }

    @Override
    public List<NotificationApp> getNotficationByPatient(int idP) {
        TypedQuery<NotificationApp> query = em.createQuery("SELECT n FROM NotificationApp n where n.patientnotif.id = :idP order by n.notified_at DESC ", NotificationApp.class);
        query.setParameter("idP", idP);
        return query.getResultList();
    }
}
