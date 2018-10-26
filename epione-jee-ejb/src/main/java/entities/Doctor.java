package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
public class Doctor extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String speciality;
	private double location;
	@OneToMany(mappedBy="doctor")
	private Set<Availability> availabilities= new HashSet<>();
	@JsonManagedReference
	@OneToMany(mappedBy="doctor")
	private Set<Appointment> appointments =new HashSet<>();
	
	@OneToMany(mappedBy="doctor")
	private Set<Path> paths=new HashSet<>();
	
	@OneToMany(mappedBy="doctor")
	private Set<Message> messages=new HashSet<>();
	
	public Doctor() {
		super();
	}
	public Doctor(String speciality, double location) {
		this.speciality = speciality;
		this.location=location;
	}
	
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public double getLocation() {
		return location;
	}
	public void setLocation(double location) {
		this.location = location;
	}
	@XmlTransient
	public Set<Availability> getAvailabilities() {
		return availabilities;
	}
	public void setAvailabilities(Set<Availability> availabilities) {
		this.availabilities = availabilities;
	}
	@XmlTransient
	public Set<Appointment> getAppointments() {
		return appointments;
	}
	
	public void setAppointments(Set<Appointment> appointments) {
		this.appointments = appointments;
	}
	@XmlTransient
	public Set<Path> getPaths() {
		return paths;
	}
	public void setPaths(Set<Path> paths) {
		this.paths = paths;
	}
	@XmlTransient
	public Set<Message> getMessages() {
		return messages;
	}
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}
