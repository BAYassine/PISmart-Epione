package services;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.User;
import interfaces.UserServiceLocal;
import interfaces.UserServiceRemote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Stateless
public class UserService implements UserServiceLocal  {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@Override
	public User findUser(String username) {
		try {
			return em.createQuery("SELECT  u from User u where u.username = :username",User.class)
					.setParameter("username", username).getSingleResult();
		}catch (NoResultException e){
			return null;
		}
	}

	@Override
	public int create(User u){
		em.persist(u);
		return u.getId();
	}

	@Override
	public void update(User u){
		em.merge(u);
	}

	/**
	 * Author : Yassine
	 */
	public void updateLoginDate(User u){
		u.setLast_login(new Date());
		em.merge(u);
	}

	/**
	 * Author : Yassine
	 */
	public void remove(User u){
		User user = em.merge(u);
		Query query = null;
		if(user.getRole().equals("ROLE_PATIENT")){
			query = em.createQuery("UPDATE Appointment a SET a.patient = null WHERE a.patient = :patient");
			query.setParameter("patient", user);
		}else if(user.getRole().equals("ROLE_DOCTOR")){
			query = em.createQuery("UPDATE Appointment a SET a.doctor = null WHERE a.doctor = :doctor");
			query.setParameter("doctor", user);
		}
		query.executeUpdate();
		em.remove(user);
	}

	/**
	 * Author : Yassine
	 */
	public long todayUsers(){
		String sql= "SELECT COUNT(u) from User u WHERE date(u.last_login) = date(current_date)";
		Query query = em.createQuery(sql);
		return (long) query.getSingleResult();
	}

	public long registeredSince(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date since;
		try {
			since = formatter.parse(date);
		} catch (ParseException e) {
			since = new Date();
		}
		String sql = "SELECT COUNT(u) from User u where date(u.registred_at) >= date(:since)";
		Query query = em.createQuery(sql).setParameter("since", since);
		return (long) query.getSingleResult();
	}

}
