package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
public class Patient extends User  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int social_number;
	
	@OneToMany(mappedBy="patient")
	private List<Path> paths;
	@JsonManagedReference
	@OneToMany(mappedBy="patient")

	private Set<Appointment> appointments = new HashSet<>();
	
	@OneToMany(mappedBy="patient")
	private List<Message> messages;

	public Patient() {
		super();
	}

	public Patient(int social_number) {
		super();
		this.social_number = social_number;
	}
	

	public Patient(int social_number, List<Path> paths, Set<Appointment> appointments, List<Message> messages) {
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
	
	@XmlTransient
	public List<Path> getPaths() {
		return paths;
	}

	public void setPaths(List<Path> paths) {
		this.paths = paths;
	}
	@XmlTransient
	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	@XmlTransient
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}


}
