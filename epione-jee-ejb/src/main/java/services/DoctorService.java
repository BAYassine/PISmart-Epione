package services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.print.Doc;

import entities.Appointment;
import entities.Doctor;
import interfaces.DoctorServiceLocal;
import interfaces.DoctorServiceRemote;

import java.util.Date;

@Stateless
public class DoctorService implements DoctorServiceLocal, DoctorServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	//By Oumayma
	public List<Doctor> getDoctorBySpeciality(int specialityID) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d WHERE d.speciality.id= :specialityID",Doctor.class);
		return query.setParameter("specialityID",specialityID).getResultList();
	}

	//By Oumayma
	public List<Doctor>  getDoctorByLocation(double location) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d WHERE d.location= :location",Doctor.class);
		return query.setParameter("location",location).getResultList();
	}

	//By Oumayma 
	public List<Doctor> getDoctorByName(String name) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d , User u ,Profile p where u.role ='ROLE_DOCTOR' and p.firstname= :name and u.id=d.id and u.profile.id=p.id ",Doctor.class);
		return query.setParameter("name",name).getResultList();
	}
	//By Oumayma
	public Doctor getDoctorById(int id) {
		return (em.find(Doctor.class, id));
	}

	//By Oumayma
	public List<Doctor>  getDoctorBySpecialitAndLocation(int specialityID, double location) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d WHERE d.location= :location AND d.speciality.id= :specialityID",Doctor.class);
		query.setParameter("location",location);
		query.setParameter("specialityID", specialityID);
		return query.getResultList();
	}

	@Override
	public List<Doctor> getDoctors() {
		 TypedQuery< Doctor> query=em.createQuery("SELECT d FROM Doctor d",Doctor.class);
		return query.getResultList();
	}

    public int create(Doctor doctor){
        doctor.setRegistred_at(new Date());
        em.persist(doctor);
        return doctor.getId();
    }

    public Doctor findDoctor(String username){
        TypedQuery<Doctor> query = em.createQuery("SELECT d FROM Doctor d WHERE d.username = :username", Doctor.class)
                .setParameter("username", username);
        return query.getSingleResult();
    }

    public void update(Doctor doctor){
		em.merge(doctor);
	}

}
