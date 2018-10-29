package interfaces;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import entities.Availability;

@Local
public interface AvailabilityServiceLocal {
	public List<Availability>checkAvailability(int idDoctor,String startDate) throws ParseException;
	public List<Availability> checkAvailabilityById(int idDoctor);

}
