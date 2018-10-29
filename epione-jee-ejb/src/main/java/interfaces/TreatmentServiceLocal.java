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
}
