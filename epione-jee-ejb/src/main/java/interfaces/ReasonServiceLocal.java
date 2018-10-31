package interfaces;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;
import javax.json.JsonObject;

import entities.Reason;


@Local
public interface ReasonServiceLocal {
	public int addReason(Reason r,int idSpeciality);
	public int updateReason(Reason r,int idSpeciality);
	public void removeReason(int idR);
	public List<Reason> searchReasonBySpeciality(int idSpeciality);

	/*Fares*/
	Set<Reason> searchReasonByDoctor(int idDoctor);
	JsonObject chosenReasons(List<Integer> idsReasons, int idDoctor);
	JsonObject getReasonById(int idReason);
	JsonObject getReasonByName(String nameReason, String updatedReasonName);
	JsonObject addReason(Reason r);
	JsonObject newReasons(List<Integer> idsReasons, int idDoctor);
}
