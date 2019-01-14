package interfaces;

import java.util.List;

import javax.ejb.Local;
import javax.json.JsonObject;

import entities.Appointment;
import entities.Rating;


@Local
public interface RateServiceLocal {
	
	public JsonObject AddRate(Rating r,int AppId);
	public Rating getRateByAppoitement(int AppId);
	public List<Rating> getRatesByPatient(String paientUserName);
	public List<Rating> getRatesByDoctor(String doctorUserName);
	public List<Rating> getAllRates();
	public JsonObject EditRate(Rating r);
	public JsonObject deleteRate(int id,int idP);
	public double DoctorRate(int idDoctor);
	public List<Rating> getAllRate();
	public List<Appointment> getAppbyuser(String username);

}
