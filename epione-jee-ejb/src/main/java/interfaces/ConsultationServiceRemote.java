package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Consultation;

@Remote
public interface ConsultationServiceRemote {
	public int addConsultation(Consultation c);
	public void deleteConsultation(int consultationId);
	public int updateConsultation(Consultation c);
	public Consultation getConsultationById(int consultationId);
	public List<Consultation> getAllConsultation();
	

}
