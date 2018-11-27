package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Patient;
import entities.Profile;

@Remote
public interface ProfileServiceRemote {
	public List<Profile> getPatientsProfie();
	public int getUserId(int id);
}
