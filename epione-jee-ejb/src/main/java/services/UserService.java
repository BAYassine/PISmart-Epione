package services;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entities.User;
import interfaces.UserServiceLocal;
import interfaces.UserServiceRemote;

@Stateless
public class UserService implements UserServiceLocal  {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@Override
	public User findUser(String username) {
		return em.createQuery("SELECT  u from User u where u.username = :username",User.class)
				.setParameter("username", username).getSingleResult();
	}

	@Override
	public int create(User u){
		em.persist(u);
		return u.getId();
	}

}
