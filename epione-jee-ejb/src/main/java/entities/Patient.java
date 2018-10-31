package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Entity
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
public class Patient extends User  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int social_number;
	
	@OneToMany(mappedBy="patient",fetch=FetchType.EAGER)
	@JsonIgnore
	private Set<Path> paths=new HashSet<>();

	@OneToMany(mappedBy="patient",fetch=FetchType.EAGER)
    @JsonIgnore
	private Set<Appointment> appointments = new HashSet<>();
	
	@OneToMany(mappedBy="patient",fetch=FetchType.EAGER)
	@JsonIgnore
	private Set<Message> messages=new HashSet<>();

	public Patient() {
		super();
	}

	public Patient(int social_number) {
		super();
		this.social_number = social_number;
	}
	

	public Patient(int social_number, Set<Path> paths, Set<Appointment> appointments, Set<Message> messages) {
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

	public Set<Path> getPaths() {
		return paths;
	}

	public void setPaths(Set<Path> paths) {
		this.paths = paths;
	}

	public Set<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public void copy(Patient p) {
		super.copy(p);
		if(this.social_number == 0 && p.social_number != 0)
			this.social_number = p.social_number;
	}
}
