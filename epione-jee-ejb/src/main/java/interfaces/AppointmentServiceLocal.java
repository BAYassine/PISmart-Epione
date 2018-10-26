package interfaces;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import entities.Appointment;

@Local
public interface AppointmentServiceLocal {
	public int addAppointment(Appointment app,int  idDoctor,int idPatient);
	public void cancelAppointment(int appId);
	public int updateAppointment(Appointment app);
	public Appointment getAppointmentById(int appointmentId);
	public List<Appointment> getAppointmentByDate(String dateapp);
	public List<Appointment> getAppointmentsByPatient(int idPatient);
	public List<Appointment> getAppointmentsByDoctor(int idDoctor);
	public List<Appointment> getAllAppointments();
	public void affectConsultation(int idAppointment,int idConsultaion);

}
