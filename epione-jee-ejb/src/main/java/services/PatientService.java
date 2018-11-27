package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import entities.Patient;
import interfaces.PatientServiceLocal;
import interfaces.PatientServiceRemote;

@Stateless
public class PatientService implements PatientServiceLocal, PatientServiceRemote {

    @PersistenceContext(unitName = "epione-jee-ejb")
    EntityManager em;

    public Patient findPatient(String username){
        Query query = em.createQuery("select p from Patient p WHERE p.username = :username");
        query.setParameter("username", username);
        return (Patient) query.getSingleResult();
    }
    public List<Patient> findAll(){
		 TypedQuery<Patient> query = em.createQuery("SELECT p FROM Patient p", Patient.class);
        return (List<Patient>) query.getResultList();
    }

    public void update(Patient patient){
        em.merge(patient);
    }

    public int create(Patient p){
        em.persist(p);
        return p.getId();
    }
}
