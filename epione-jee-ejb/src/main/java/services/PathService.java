package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import interfaces.PathServiceLocal;
import interfaces.PathServiceRemote;

public class PathService implements PathServiceLocal, PathServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
}
