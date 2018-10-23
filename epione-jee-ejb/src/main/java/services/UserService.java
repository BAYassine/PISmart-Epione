package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import interfaces.UserServiceLocal;
import interfaces.UserServiceRemote;

@Stateless
public class UserService implements UserServiceLocal , UserServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

}
