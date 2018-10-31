package interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import entities.Report;


@Local
public interface ReportServiceLocal {
	
	public int addReport(Report report);
	public int updateReport(Report report);
	public void deleteReport(Report report);
	public List<Report> getAllReports();
	public Report getReportById(int id);
	public List<Report> getReportByContent(String content);
	public List<Report> getReportByDate(Date date);
	public List<Report> getReportDateGreaterThen(Date date);
	public List<Report> getReportDateLessThen(Date date);
	public List<Report> getReportByDateContent(Date date, String content);
	List<Report> getPatientsReport(int id);


}
