package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.Doctor;
import entities.Message;
import entities.Patient;
import interfaces.MessageServiceLocal;
import interfaces.MessageServiceRemote;

import java.util.Date;
import java.util.List;

@Stateless
public class MessageService implements MessageServiceLocal, MessageServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	/**
	 * Author : Oumayma
	 */
	@Override
	public int sendMessagePatient(int idP, int idD, Message msg) {
		if (msg!=null)
		{   Patient p=em.find(Patient.class, idP);
		    msg.setPatient(p);
		    Doctor d=em.find(Doctor.class,idD);
		    msg.setDoctor(d);
		    msg.setDate_msg(new Date());
		    msg.setSeen(false);
			em.persist(msg);
			return 1;
		}
		return 0;
	}
	public List<Message> PatientMessages(int idP, int nbMsg){
		String sql = "SELECT m FROM Message m where m.patient.id = :idP order by m.date_msg DESC ";
		Query query = em.createQuery(sql).setParameter("idP", idP);
		return query.setMaxResults(nbMsg).getResultList();
	}


	/**
	 * Author : Yassine
	 */
	public List<Message> inbox(Doctor doctor, int limit){
		String sql = "SELECT m FROM Message m where m.doctor = :doctor order by m.date_msg DESC ";
		Query query = em.createQuery(sql).setParameter("doctor", doctor);
		return query.setMaxResults(limit).getResultList();
	}

	/**
	 * Author : Yassine
	 */
	public long unreadMessages(Doctor doctor){
		String sql = "SELECT count(m) FROM Message m where m.doctor = :doctor and m.seen = false ";
		Query query = em.createQuery(sql).setParameter("doctor", doctor);
		return (long) query.getSingleResult();
	}

	
}
