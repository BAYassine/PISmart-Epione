package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import interfaces.ConsultationServiceLocal;
import interfaces.ConsultationServiceRemote;

@Stateless
public class ConsultationService implements ConsultationServiceLocal , ConsultationServiceRemote {
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
}
