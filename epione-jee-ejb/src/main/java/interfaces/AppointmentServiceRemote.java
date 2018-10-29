package interfaces;

import entities.Appointment;
import entities.Doctor;

import javax.ejb.Remote;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Remote
public interface AppointmentServiceRemote {
    public int addAppointment(Appointment app,int  idDoctor,int idPatient,int idReason);
    public boolean cancelAppointment(int appId);
    public int updateAppointment(Appointment app, int idR);
    public Appointment getAppointmentById(int appointmentId);
    public List<Appointment> getAppointmentByDate(String dateapp) throws ParseException;
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
