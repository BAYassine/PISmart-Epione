package interfaces;

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

}
