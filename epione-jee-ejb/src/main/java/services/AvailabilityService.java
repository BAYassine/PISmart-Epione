package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AvailabilityService implements AvailabilityServiceLocal , AvailabilityServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
}
