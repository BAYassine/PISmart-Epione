package interfaces;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import entities.Availability;

@Remote
public interface AvailabilityServiceRemote {
	public List<Availability> checkAvailability(int idDoctor,String startDate)throws ParseException;
	public List<Availability> checkAvailabilityById(int idDoctor);
}
