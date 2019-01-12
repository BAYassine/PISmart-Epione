package interfaces;

import entities.Appointment;
import entities.Doctor;

import javax.ejb.Remote;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Remote
public interface AppointmentServiceRemote {

	/**
	 * Author : Yassine
	 */
	Date averageDuration(Doctor doctor);
	List<Appointment> upcoming(Doctor doctor);
	double averageAppointements(Doctor doctor);
	Appointment ongoing(Doctor doctor);
	long totalAppointements(Doctor doctor, String from);
	Map<String, Long> appointmentPerDay(Doctor doctor, String since);
	Map<String, Long> appointmentPerMonth(Doctor doctor,String since);
	Map<String, Long> appointmentPerYear(Doctor doctor,String since);
	Long totalPatient(Doctor doctor);
	boolean startEndAppointment(int id, boolean action, int uid);

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
