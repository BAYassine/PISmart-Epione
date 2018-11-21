package interfaces;

import entities.Doctor;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface DoctorServiceRemote {
	public List<Doctor> getDoctorBySpeciality(int specialityID);
	public List<Doctor> getDoctorByLocation(double latitude,double longitude);
	public List<Doctor> getDoctorByCity(String city);
	public List<Doctor> getDoctorByName(String name);
	public Doctor getDoctorById(int id);
	public List<Doctor> getDoctorBySpecialitAndLocation(int specialityID,double latitude,double longitude);
	public List<Doctor> getDoctorBySpecialitAndCity(int specialityID,String city);
	public List<Doctor> getDoctorByNameAndLocation(String name,double latitude,double longitude);
	public List<Doctor> getDoctorByNameAndCity(String name,String city);
	public List<Doctor> getDoctors();
	/**
	 * Author : Yassine
	 */
	public int create(Doctor doctor);
	Doctor findDoctor(String username);
	void update(Doctor doctor);
}
