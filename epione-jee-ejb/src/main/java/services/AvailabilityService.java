package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import entities.Appointment;
import entities.Availability;
import interfaces.AvailabilityServiceLocal;
import interfaces.AvailabilityServiceRemote;

@Stateless
public class AvailabilityService implements AvailabilityServiceLocal, AvailabilityServiceRemote {
    @PersistenceContext(unitName = "epione-jee-ejb")
    EntityManager em;

    @Override
    public List<Availability> checkAvailability(int idDoctor, String startDate) throws ParseException {
        Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate);
        TypedQuery<Availability> query = em.createQuery("SELECT a FROM Availability a where a.doctor.id= :idDoctor and :startDate"
                + " BETWEEN start_date and end_date", Availability.class);
        System.out.println("requete : " + query.toString());
        query.setParameter("idDoctor", idDoctor);
        query.setParameter("startDate", d1);
        return (query.getResultList());
    }

    @Override
    public List<Availability> checkAvailabilityById(int idDoctor) {
        TypedQuery<Availability> query = em.createQuery("SELECT a FROM Availability a where a.doctor.id= :idDoctor", Availability.class);
        query.setParameter("idDoctor", idDoctor);
        return query.getResultList();
    }
}
