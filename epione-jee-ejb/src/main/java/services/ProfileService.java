package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import interfaces.ProfileServiceLocal;

@Stateless
public class ProfileService implements ProfileServiceLocal, PathServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
}
