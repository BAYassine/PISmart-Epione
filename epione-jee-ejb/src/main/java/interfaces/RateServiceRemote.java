package interfaces;

import java.util.List;

import javax.ejb.Remote;
import javax.json.JsonObject;

import entities.Rating;


@Remote
public interface RateServiceRemote {
	
	public JsonObject AddRate(Rating r,int AppId);
	public Rating getRateByAppoitement(int AppId);
	public List<Rating> getRatesByPatient(String paientUserName);
	public List<Rating> getRatesByDoctor(String doctorUserName);
	public List<Rating> getAllRates();
	public JsonObject EditRate(Rating r);
	public JsonObject deleteRate(int id,int idP);
	public double DoctorRate(int idDoctor);

}
