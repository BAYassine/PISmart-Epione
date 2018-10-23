package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import interfaces.TreatmentServiceLocal;
import interfaces.TreatmentServiceRemote;

@Stateless
public class TreatmentService implements TreatmentServiceLocal, TreatmentServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
}
