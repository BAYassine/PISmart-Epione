package interfaces;

import java.util.List;

import javax.ejb.Remote;

import entities.Appointment;

@Remote
public interface AppointmentServiceRemote {
	public int addAppointment(Appointment app,int  idDoctor,int idPatient);
	public void cancelAppointment(int appId);
	public int updateAppointment(Appointment app);
	public Appointment getAppointmentById(int appointmentId);
	public List<Appointment> getAllAppointments();
	public void affectConsultation(int idAppointment,int idConsultaion);
	


}
