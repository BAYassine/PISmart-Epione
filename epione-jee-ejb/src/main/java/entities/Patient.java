package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
@Entity
public class Patient extends User implements Serializable{
	
	private int social_number;
	@OneToMany(mappedBy="patient")
	private List<Path> paths;
	
	@OneToMany(mappedBy="patient")
	private List<Appointments> appointments;
	
	@OneToMany(mappedBy="patient")
	private List<Message> messages;

	public Patient() {
		super();
	}

	public Patient(int social_number) {
		super();
		this.social_number = social_number;
	}
	

	public Patient(int social_number, List<Path> paths, List<Appointments> appointments, List<Message> messages) {
		super();
		this.social_number = social_number;
		this.paths = paths;
		this.appointments = appointments;
		this.messages = messages;
	}

	public int getSocial_number() {
		return social_number;
	}

	public void setSocial_number(int social_number) {
		this.social_number = social_number;
	}
	

	public List<Path> getPaths() {
		return paths;
	}

	public void setPaths(List<Path> paths) {
		this.paths = paths;
	}

	public List<Appointments> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointments> appointments) {
		this.appointments = appointments;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}


}
