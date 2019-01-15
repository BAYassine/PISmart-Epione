package interfaces;

import entities.Doctor;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DoctorServiceLocal {
	public List<Doctor> getDoctorBySpeciality(int specialityID);
	public List<Doctor> getDoctorByLocation(String latitude,String longitude);
	public List<Doctor> getDoctorByCity(String city);
	public List<Doctor> getDoctorByName(String name);
	public Doctor getDoctorById(int id);
	public List<Doctor> getDoctorBySpecialitAndLocation(int specialityID,String latitude,String longitude);
	public List<Doctor> getDoctorBySpecialitAndCity(int specialityID,String city);
	public List<Doctor> getDoctorByNameAndLocation(String name,String latitude,String longitude);
	public List<Doctor> getDoctorByNameAndSpeciality(String name,int id);
	public List<Doctor> getDoctorByNameAndSpecialitAndLocation(String name,int specialityID,String latitude,String longitude);
	public List<Doctor> getDoctors();
	
	/**
	 * Author : Yassine
	 */
    public int create(Doctor doctor);
    Doctor findDoctor(String username);
	public void update(Doctor d);
  
}
