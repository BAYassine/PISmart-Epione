package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PathService implements PathServiceLocal, PathServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
}
