package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Treatment;

@Remote
public interface TreatmentServiceRemote {

	public int addTreatment(Treatment treatment);
	public int updateTreatment(Treatment treatment);
	public void deleteTreatment(Treatment treatment);
	public List<Treatment> getAllTreatments();
	public Treatment getTreatmentById(int id);
}
