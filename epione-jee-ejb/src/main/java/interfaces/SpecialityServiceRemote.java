package interfaces;

import javax.ejb.Remote;
import javax.json.JsonObject;

import entities.Speciality;

@Remote
public interface SpecialityServiceRemote {

	JsonObject addSpeciality(Speciality s);

}
