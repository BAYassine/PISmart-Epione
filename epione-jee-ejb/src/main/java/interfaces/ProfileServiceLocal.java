package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Patient;
import entities.Profile;

@Local
public interface ProfileServiceLocal {
	public List<Profile> getPatientsProfie();
	public int getUserId(int id);
	
}
