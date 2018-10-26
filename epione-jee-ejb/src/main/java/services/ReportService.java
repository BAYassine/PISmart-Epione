package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entities.Report;
import interfaces.ReportServiceLocal;
import interfaces.ReportServiceRemote;

@Stateless
public class ReportService implements ReportServiceLocal, ReportServiceRemote{
	@PersistenceContext(unitName="epione-jee-ejb")
	EntityManager em;

	@Override
	public int addReport(Report report) {
		em.persist(report);
		return report.getId();
	}

	@Override
	public int updateReport(Report report) {
		Report t = em.find(Report.class, report.getId());
		t.setConsultation(report.getConsultation());
		t.setContent(report.getContent());
		t.setDate_rep(report.getDate_rep());
		return report.getId();
	}

	@Override
	public void deleteReport(Report report) {
		em.remove(report);
		
	}

	@Override
	public List<Report> getAllReports() {
		TypedQuery<Report> query = em.createQuery("SELECT p FROM Report p", Report.class);
		 System.out.println("SELECT p FROM Report p");
		 return (List<Report>) query.getResultList();
	}

	@Override
	public Report getReportById(int id) {
		// TODO Auto-generated method stub
		return em.find(Report.class, id);
	}

	
}
