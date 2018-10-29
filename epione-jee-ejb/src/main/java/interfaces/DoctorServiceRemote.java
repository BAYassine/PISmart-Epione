package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Doctor;

@Remote
public interface DoctorServiceRemote {
	public List<Doctor> getDoctorBySpeciality(int specialityID);
	public List<Doctor> getDoctorByLocation(double location);
	public List<Doctor> getDoctorByName(String name);
	public Doctor getDoctorById(int id);
	public List<Doctor> getDoctorBySpecialitAndLocation(int specialityID,double location);
	public List<Doctor> getDoctors();
}
