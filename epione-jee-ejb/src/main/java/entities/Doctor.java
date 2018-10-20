package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Doctor extends User implements Serializable{
	private int id;
	private String speciality;
	
	@OneToMany(mappedBy="doctor")
	private List<Availability> availability;
	
	@OneToMany(mappedBy="doctor")
	private List<Appointments> appointments;
	
	@OneToMany(mappedBy="doctor")
	private List<Path> paths;
	
	@OneToMany(mappedBy="doctor")
	private List<Message> messages;
	
	public Doctor() {
		super();
	}
	public Doctor(int id, String speciality) {
		super();
		this.id = id;
		this.speciality = speciality;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public List<Availability> getAvailability() {
		return availability;
	}
	public void setAvailability(List<Availability> availability) {
		this.availability = availability;
	}
	public List<Appointments> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<Appointments> appointments) {
		this.appointments = appointments;
	}
	public List<Path> getPaths() {
		return paths;
	}
	public void setPaths(List<Path> paths) {
		this.paths = paths;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	

}
