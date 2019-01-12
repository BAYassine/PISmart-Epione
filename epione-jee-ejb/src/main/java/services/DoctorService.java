package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
	public List<Doctor>  getDoctorByLocation(String latitude,String longitude) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d WHERE d.latitude= :latitude AND d.longitude= :longitude",Doctor.class);
		return query.setParameter("latitude",latitude).setParameter("longitude",longitude).getResultList();
	}

	//By Oumayma 
	public List<Doctor> getDoctorByName(String name) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d , User u ,Profile p where u.role ='ROLE_DOCTOR' and  (p.firstname LIKE CONCAT('%',:name,'%') or p.lastname LIKE CONCAT('%',:name,'%')) and u.id=d.id and u.profile.id=p.id ",Doctor.class);
		System.out.println("queryy" +query.toString());
		return query.setParameter("name",name).getResultList();
	}
	//By Oumayma
	public Doctor getDoctorById(int id) {
		return (em.find(Doctor.class, id));
	}

	//By Oumayma
	public List<Doctor>  getDoctorBySpecialitAndLocation(int specialityID, String latitude,String longitude) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d WHERE d.latitude= :latitude AND d.longitude= :longitude AND d.speciality.id= :specialityID",Doctor.class);
		query.setParameter("latitude",latitude);
		query.setParameter("longitude", longitude);
		query.setParameter("specialityID", specialityID);
		return query.getResultList();
	}

	@Override
	public List<Doctor> getDoctors() {
		 TypedQuery< Doctor> query=em.createQuery("SELECT d FROM Doctor d",Doctor.class);
		return query.getResultList();
	}

	@Override
	public List<Doctor> getDoctorByCity(String city) {
	    TypedQuery<Doctor> query = em.createQuery("SELECT d FROM Doctor d WHERE d.city = :city", Doctor.class)
                .setParameter("city", city);
        return query.getResultList();
	}

	@Override
	public List<Doctor> getDoctorBySpecialitAndCity(int specialityID, String city) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d WHERE d.speciality.id= :specialityID AND d.city= :city",Doctor.class);
		return query.setParameter("specialityID",specialityID).setParameter("city", city).getResultList();
	}

	@Override
	public List<Doctor> getDoctorByNameAndLocation(String name, String latitude, String longitude) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d , User u ,Profile p where u.role ='ROLE_DOCTOR' and (p.firstname LIKE CONCAT('%',:name,'%') or p.lastname LIKE CONCAT('%',:name,'%')) and u.id=d.id and u.profile.id=p.id and d.latitude= :latitude and d.longitude= :longitude",Doctor.class);
		return query.setParameter("name",name).setParameter("latitude", latitude).setParameter("longitude", longitude).getResultList();
	}
	@Override
	public List<Doctor> getDoctorByNameAndSpeciality(String name,int id) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d , User u ,Profile p where u.role ='ROLE_DOCTOR' and (p.firstname LIKE CONCAT('%',:name,'%') or p.lastname LIKE CONCAT('%',:name,'%')) and u.id=d.id and u.profile.id=p.id and d.speciality.id= :idS",Doctor.class);
		return query.setParameter("name",name).setParameter("idS", id).getResultList();
	}
   /** 
    * YASSINE
    * **/
    public int create(Doctor doctor){
        doctor.setRegistered_at(new Date());
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

	@Override
	public List<Doctor> getDoctorByNameAndSpecialitAndLocation(String name, int specialityID, String latitude,
			String longitude) {
		TypedQuery<Doctor> query=em.createQuery("SELECT d from Doctor d , User u ,Profile p where u.role ='ROLE_DOCTOR' and (p.firstname LIKE CONCAT('%',:name,'%') or p.lastname LIKE CONCAT('%',:name,'%')) and u.id=d.id and u.profile.id=p.id and d.latitude= :latitude and d.longitude= :longitude and d.speciality.id= :specialityID",Doctor.class);
		return query.setParameter("name",name).setParameter("latitude", latitude).setParameter("longitude", longitude).setParameter("specialityID", specialityID).getResultList();

	}






}
