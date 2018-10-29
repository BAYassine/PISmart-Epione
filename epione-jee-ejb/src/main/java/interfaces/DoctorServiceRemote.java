package interfaces;

import javax.ejb.Local;
import javax.ejb.Remote;

import entities.Doctor;

import java.util.List;

@Remote
public interface DoctorServiceRemote {
	public List<Doctor> getDoctorBySpeciality(int specialityID);
	public List<Doctor> getDoctorByLocation(double location);
	public List<Doctor> getDoctorByName(String name);
	public Doctor getDoctorById(int id);
	public List<Doctor> getDoctorBySpecialitAndLocation(int specialityID,double location);
	public List<Doctor> getDoctors();

	/**
	 * Author : Yassine
	 */
	public int create(Doctor doctor);
	Doctor findDoctor(String username);
	void update(Doctor doctor);
}
