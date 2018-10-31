package interfaces;

import javax.ejb.Local;
import javax.json.JsonObject;

import entities.Speciality;

@Local
public interface SpecialityServiceLocal {

	JsonObject addSpeciality(Speciality s);

	void removeSpeciality(int idS);

	JsonObject chosenSpeciality(int idS, int idDoctor);

	JsonObject getSpecialityById(int idS);

	JsonObject changeSpecName(String specName, String newspecName);

}
