package interfaces;

import java.util.List;

import javax.ejb.Local;

import entities.Appointment;

@Local
public interface AppointmentServiceLocal {
	public int addAppointment(Appointment app,int  idDoctor,int idPatient);
	public void cancelAppointment(int appId);
	public int updateAppointment(Appointment app);
	public Appointment getAppointmentById(int appointmentId);
	public List<Appointment> getAllAppointments();
	public void affectConsultation(int idAppointment,int idConsultaion);

}
