package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Consultation;

@Local
public interface ConsultationServiceLocal {
	public int addConsultation(Consultation c);
	public void deleteConsultation(int consultationId);
	public int updateConsultation(Consultation c);
	public List<Consultation> getConsultationById(int consultationId);
	public List<Consultation> getAllConsultation();
	List<Consultation> getPricyConsultation(double price);
	List<Consultation> getBestConsultations();
	List<Consultation> getDoctorConsultations(int doctor);
	int addConsultation(Consultation c, int t);
	

}
