package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import interfaces.ProfileServiceLocal;
import interfaces.ProfileServiceRemote;

@Stateless
public class ProfileService implements ProfileServiceLocal, ProfileServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

}
