package interfaces;

import java.text.ParseException;
import entities.Appointment;
import entities.Doctor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;


@Local
public interface AppointmentServiceLocal {

    /**
     * Author : Yassine
     */
    Date averageDuration(Doctor doctor);
	List<Appointment> upcoming(Doctor doctor);
	double averageAppointements(Doctor doctor);
	Appointment ongoing(Doctor doctor);
	long totalAppointements(Doctor doctor, String from);
    Map<Date, Long> appointmentPerDay(Doctor doctor);

	/**
	 * Author : Oumayma
	 */
	List<Appointment> getPatientsAppointmentByDate(String date, int idP) throws ParseException;
	List<Appointment> getDoctorsAppointmentByDate(String date, int idD) throws ParseException;
	List<Appointment> getAppointmentsByDoctor(int idDoctor);
	List<Appointment> getAppointmentsByPatient(int idPatient);
	void affectConsultation(int idAppointment, int idConsultaion);
	List<Appointment> getAllAppointments();
	List<Appointment> getAppointmentByDate(String dateapp) throws ParseException;
	Appointment getAppointmentById(int appointmentId);
	int updateAppointment(Appointment app, int idP);
	boolean cancelAppointment(int appId, int idP);
	int addAppointment(Appointment app, int idPatient, String emailPatient) throws ParseException;
	void deleteAppointment(int idA, int idP);
}
