package services;

import java.util.Date;
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
		t.setPathFile(report.getPathFile());
		return report.getId();
	}

	@Override
	public void deleteReport(Report report) {
		Report t = em.find(Report.class, report.getId());
		em.remove(t);
		
	}

	@Override
	public List<Report> getAllReports() {
		 System.out.println("req num 1) SELECT r FROM Report r");
		TypedQuery<Report> query = em.createQuery("SELECT r FROM Report r", Report.class);
		 return (List<Report>) query.getResultList();
	}
	
	@Override
	public List<Report> getReportByContent(String content) {
		 System.out.println("req num 2) SELECT r FROM Report r WHERE r.content like :content");
		TypedQuery<Report> query = em.createQuery("SELECT r FROM Report r WHERE r.content like :content", Report.class);
		 return (List<Report>) query.setParameter("content", "%"+content+"%").getResultList();
	}
	
	@Override
	public List<Report> getReportByDate(Date date) {
		if(date != null) {
		 System.out.println("req num 3) SELECT r FROM Report r WHERE r.date_rep = :date");
		TypedQuery<Report> query = em.createQuery("SELECT r FROM Report r WHERE r.date_rep = :date", Report.class);
		 return (List<Report>) query.setParameter("date", date).getResultList();
		}else return null;
	}
	
	@Override
	public List<Report> getReportDateGreaterThen(Date date) {
		if(date != null) {
		 System.out.println("req num 4) SELECT r FROM Report r WHERE r.date_rep > :date");
		TypedQuery<Report> query = em.createQuery("SELECT r FROM Report r WHERE r.date_rep > :date", Report.class);
		 return (List<Report>) query.setParameter("date", date).getResultList();
		 }else return null;
	}
	
	@Override
	public List<Report> getReportDateLessThen(Date date) {
		if(date != null) {

		 System.out.println("req num 5) SELECT r FROM Report r WHERE r.date_rep < :date");
			TypedQuery<Report> query = em.createQuery("SELECT r FROM Report r WHERE r.date_rep < :date", Report.class);
			 return (List<Report>) query.setParameter("date", date).getResultList();
		 }else return null;

		}
	
	@Override
	public List<Report> getReportByDateContent(Date date, String content) {
		if(date != null) {

		 System.out.println("req num 6) SELECT r FROM Report r WHERE r.date_rep = :date and r.content like :content");
			TypedQuery<Report> query = em.createQuery("SELECT r FROM Report r WHERE r.date_rep = :date and r.content like :content", Report.class);
			 return (List<Report>) query.setParameter("date", date).setParameter("content", "%"+content+"%").getResultList();
		 }else return null;

		}

	@Override
	public Report getReportById(int id) {
		return em.find(Report.class, id);
	}

	
}
