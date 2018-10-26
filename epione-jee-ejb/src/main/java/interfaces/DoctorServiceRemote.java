package interfaces;

import javax.ejb.Remote;

import entities.Doctor;

@Remote
public interface DoctorServiceRemote {
	public Doctor getDoctorBySpeciality(String speciality);
	public Doctor getDoctorByLocation(double location);

}
