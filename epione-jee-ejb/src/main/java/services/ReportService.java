package services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import interfaces.ReportServiceLocal;
import interfaces.ReportServiceRemote;

@Stateless
public class ReportService implements ReportServiceLocal, ReportServiceRemote{
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;
}
