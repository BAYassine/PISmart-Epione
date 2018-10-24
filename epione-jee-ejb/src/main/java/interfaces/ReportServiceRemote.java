package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Report;

@Remote
public interface ReportServiceRemote {
	public int addReport(Report report);
	public int updateReport(Report report);
	public void deleteReport(Report rreport);
	public List<Report> getAllReports();
	public Report getReportById(int id);

}
