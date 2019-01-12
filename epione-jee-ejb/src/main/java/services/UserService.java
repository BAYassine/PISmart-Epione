package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.User;
import entities.Patient;
import interfaces.UserServiceLocal;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public int create(Patient p){
		em.persist(p);
		return p.getId();
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
		String sql = "SELECT COUNT(u) from User u where date(u.registered_at) >= date(:since)";
		Query query = em.createQuery(sql).setParameter("since", since);
		return (long) query.getSingleResult();
	}

	public Map<String, Long> subscrtionsPerMonth(){
		String sql = "SELECT COUNT(*) total, DATE_FORMAT(registered_at, '%Y-%m') month, Date(registered_at) FROM user " +
				"GROUP BY (DATE_FORMAT(registered_at, '%y-%m')) LIMIT 12";
		Query query =em.createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		Map<String, Long> map = new HashMap<>();
		list.forEach( k -> map.put((String) k[1], ((BigInteger)k[0]).longValue()));
		return map;
	}

	public Map<Date, Long> connectionsPerDay(){
		String sql = "SELECT COUNT(*), last_login FROM user " +
				"WHERE last_login IS NOT NULL " +
				"GROUP BY (DATE_FORMAT(last_login, '%y-%m-%d')) LIMIT 15";
		Query query =em.createNativeQuery(sql);
		List<Object[]> list = query.getResultList();
		Map<Date, Long> map = new HashMap<>();
		list.forEach( k -> map.put((Date) k[1], ((BigInteger)k[0]).longValue()));
		return map;
	}

	public List<User> latestRegistrations(int limit){
		Query query = em.createQuery("SELECT u FROM User u order by u.registered_at DESC");
		if (limit != 0)
			query.setMaxResults(limit);
		return query.getResultList();
	}


}
