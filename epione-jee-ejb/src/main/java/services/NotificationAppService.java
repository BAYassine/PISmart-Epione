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
        NotificationApp na = new NotificationApp();
        na.setNotified_at(new Date());
        na.setNew_Appointement_Date(app.getDate_start());
        na.setPatientnotif(app.getPatient());
        na.setConfirmation(Confiramtion.WAITING);
        na.toString();
        em.persist(na);
        return Json.createObjectBuilder().add("Success", "Notification Envoyé").add("id", na.toString()).build();
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
                .setParameter("idP", idP);
        NotificationApp na = query.getSingleResult();
        System.out.println(na.toString());
        if (na.getConfirmation().equals("YES")) {
            return Json.createObjectBuilder().add("Ooups !", "Vous avez Déja Changé le RDV").build();
        } else if (na.getConfirmation().equals("WAITING")) {
            return Json.createObjectBuilder().add("Success !", "Confirmation Effectué").build();
        } else
            return Json.createObjectBuilder().add("Ooups !", "Vous avez Déja Annulé le changement dhoraire").build();

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
