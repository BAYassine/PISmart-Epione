package interfaces;

import java.util.List;

import javax.ejb.Local;
import javax.json.JsonObject;

import entities.Speciality;

@Local
public interface SpecialityServiceLocal {
    List<Speciality> getAllSpecialities();
    JsonObject addSpeciality(Speciality s);

	void removeSpeciality(int idS);

	JsonObject chosenSpeciality(int idS, int idDoctor);

	JsonObject getSpecialityById(int idS);

	JsonObject changeSpecName(String specName, String newspecName);

}
