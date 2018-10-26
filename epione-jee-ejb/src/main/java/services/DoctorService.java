package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Doctor;
import interfaces.DoctorServiceLocal;
import interfaces.DoctorServiceRemote;

@Stateless
public class DoctorService implements DoctorServiceLocal, DoctorServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@Override
	public Doctor getDoctorBySpeciality(String speciality) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d WHERE d.speciality= :speciality",Doctor.class);
		return query.setParameter("speciality",speciality).getSingleResult();
	}

	@Override
	public Doctor getDoctorByLocation(double location) {
		// TODO Auto-generated method stub
		return null;
	}
}
