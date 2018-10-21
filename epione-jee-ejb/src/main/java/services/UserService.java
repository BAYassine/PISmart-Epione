package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserService implements UserServiceLocal , UserServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

}
