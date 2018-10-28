package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Doctor;

@Local
public interface DoctorServiceLocal {
	public List<Doctor> getDoctorBySpeciality(int specialityID);
	public List<Doctor> getDoctorByLocation(double location);
	public List<Doctor> getDoctorByName(String name);
	public Doctor getDoctorById(int id);
	public List<Doctor> getDoctorBySpecialitAndLocation(int specialityID,double location);
	public List<Doctor> getDoctors();

}
