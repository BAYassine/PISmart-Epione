package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Appointment;
import entities.Doctor;
import entities.Path;
import entities.Profile;
import entities.Treatment;
import entities.User;
import interfaces.ProfileServiceLocal;
import interfaces.ProfileServiceRemote;

@Stateless
public class ProfileService implements ProfileServiceLocal, ProfileServiceRemote {

	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
	
	
	public List<Profile> getPatientsProfie() {
	 TypedQuery<Profile> query = em.createQuery("SELECT p FROM Profile p WHERE p.id in (SELECT u.id FROM User u WHERE u.role Like 'ROLE_PATIENT' )", Profile.class);
	 return (List<Profile>) query.getResultList();

}
	public int getUserId(int id) {
		 TypedQuery<Integer> query = em.createQuery("SELECT p.user.id FROM Profile p WHERE p.id = :id ", Integer.class);
		 return (Integer) query.setParameter("id", id).getSingleResult();

	}
	
	/*
	 * 	@Override
	public Doctor getPathDoctor(int id) {
			System.out.println("req num 7) SELECT p.doctor FROM Path p WHERE p.id = :id");
		 TypedQuery<Doctor> query = em.createQuery("SELECT p.doctor FROM Path p WHERE p.id = :id", Doctor.class);
		 return (Doctor) query.setParameter("id", id).getSingleResult();

	}*/
}
