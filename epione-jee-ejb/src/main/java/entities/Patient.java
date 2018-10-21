package entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Patient extends User  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int social_number;
	
	@OneToMany(mappedBy="patient")
	private List<Path> paths;
	
	@OneToMany(mappedBy="patient")
	private List<Appointment> appointments;
	
	@OneToMany(mappedBy="patient")
	private List<Message> messages;

	public Patient() {
		super();
	}

	public Patient(int social_number) {
		super();
		this.social_number = social_number;
	}
	

	public Patient(int social_number, List<Path> paths, List<Appointment> appointments, List<Message> messages) {
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

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}


}
