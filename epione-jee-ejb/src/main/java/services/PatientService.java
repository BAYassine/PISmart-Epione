package services;

import entities.Patient;
import interfaces.PatientServiceLocal;
import interfaces.PatientServiceRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PatientService implements PatientServiceLocal, PatientServiceRemote {

    @PersistenceContext(unitName = "epione-jee-ejb")
    EntityManager em;

    public Patient findPatient(String username){
        Query query = em.createQuery("select p from Patient p WHERE p.username = :username");
        query.setParameter("username", username);
        return (Patient) query.getSingleResult();
    }

    public void update(Patient patient){
        em.merge(patient);
    }

    public int create(Patient p){
        em.persist(p);
        return p.getId();
    }
}
