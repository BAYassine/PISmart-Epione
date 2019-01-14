package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Treatment;

@Local
public interface TreatmentServiceLocal {

	public int addTreatment(Treatment treatment);
	public int updateTreatment(Treatment treatment);
	public void deleteTreatment(Treatment treatment);
	public List<Treatment> getAllTreatments();
	public Treatment getTreatmentById(int id);
	public List<Treatment> getTreatmentsByRecomDoc(String treat);
	public List<Treatment> getTreatmentsByDesc(String desc);
	public List<Treatment> getTreatmentsByRecomDocAndDesc(String recDoc , String desc);
	List<Treatment> getPatientsTreatment(int id);
	List<Treatment> getDoctorsTreatment(int id);
	public List<Treatment> getTreatmentsPath(int idPath);
	public Long countTreatPath(int idPath);
}
