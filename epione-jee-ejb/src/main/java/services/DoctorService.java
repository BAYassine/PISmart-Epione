package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DoctorService implements DoctorServiceLocal, DoctorServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
}
