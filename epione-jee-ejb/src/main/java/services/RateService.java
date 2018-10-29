package services;


import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Rating;
import interfaces.RateServiceLocal;


@Stateless
public class RateService implements RateServiceLocal {

	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@Override
	public int AddRate(Rating r) {
		r.setCreated_at(new Date());
		em.persist(r);
		return r.getId();
	}

	@Override
	public int EditRate(Rating r,int rate, String comment) {
		Rating rating = em.find(Rating.class, r.getId());
		rating.setComment(comment);
		rating.setRate(rate);
		return rating.getId();
	}
	 @Override
	 public List<Rating> getAllRate(){
			TypedQuery<Rating> req = em.createQuery("SELECT r FROM Rating r", Rating.class);
			List<Rating> res = req.getResultList();
			System.out.println("****************** List des Rating ******************");
			System.out.println(res);
			return res;
	 }
	

}
