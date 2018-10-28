package interfaces;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;

import entities.Appointment;

@Remote
public interface AppointmentServiceRemote {
	public int addAppointment(Appointment app,int  idDoctor,int idPatient,int idReason);
	public boolean cancelAppointment(int appId);
	public int updateAppointment(Appointment app, int idR);
	public Appointment getAppointmentById(int appointmentId);
	public List<Appointment> getAppointmentByDate(String dateapp) throws ParseException ;
	public List<Appointment> getAppointmentsByPatient(int idPatient);
	public List<Appointment> getAppointmentsByDoctor(int idDoctor);
	public List<Appointment> getAllAppointments();
	public void affectConsultation(int idAppointment,int idConsultaion);
	


}
