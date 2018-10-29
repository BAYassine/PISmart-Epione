package interfaces;

import entities.Appointment;
import entities.Doctor;

import javax.ejb.Remote;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Remote
public interface AppointmentServiceRemote {
	
	public int addAppointment(Appointment app,int idPatient)throws ParseException;
	public boolean cancelAppointment(int appId,int idP);
	public int updateAppointment(Appointment app);
	public Appointment getAppointmentById(int appointmentId);
	public void deleteAppointment(int idA);
	public List<Appointment> getAppointmentByDate(String dateapp) throws ParseException ;
	public List<Appointment> getAppointmentsByPatient(int idPatient);
	public List<Appointment> getAppointmentsByDoctor(int idDoctor);
	public List<Appointment> getAllAppointments();
	public void affectConsultation(int idAppointment,int idConsultaion);
	
    /**
     * Author : Yassine
     */
    Date averageDuration(Doctor doctor);
    List<Appointment> upcoming(Doctor doctor);
    double averageAppointements(Doctor doctor);
    Appointment ongoing(Doctor doctor);
    long totalAppointements(Doctor doctor, String from);
}
