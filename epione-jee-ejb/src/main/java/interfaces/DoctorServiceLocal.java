package interfaces;

import javax.ejb.Local;

import entities.Doctor;

import java.util.List;

@Local
public interface DoctorServiceLocal {
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
