package interfaces;

import javax.ejb.Local;

import entities.Doctor;

@Local
public interface DoctorServiceLocal {
	public Doctor getDoctorBySpeciality(String speciality);
	public Doctor getDoctorByLocation(double location);

}
