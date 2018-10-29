package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Doctor;
import entities.Message;
import interfaces.MessageServiceLocal;
import interfaces.MessageServiceRemote;

import java.util.List;

@Stateless
public class MessageService implements MessageServiceLocal, MessageServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	/**
	 * Author : Yassine
	 */
	public List<Message> inbox(Doctor doctor, int limit){
		String sql = "SELECT m FROM Message m where m.doctor = :doctor order by m.date_msg DESC ";
		Query query = em.createQuery(sql).setParameter("doctor", doctor);
		return query.setMaxResults(limit).getResultList();
	}
}
