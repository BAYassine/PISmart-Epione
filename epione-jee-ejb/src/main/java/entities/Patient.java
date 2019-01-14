package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement
@PrimaryKeyJoinColumn(name = "id")
public class Patient extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int social_number;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Path> paths = new HashSet<>();

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Message> messages = new HashSet<>();

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

    public Patient(User u) {
        this.username = u.username;
        this.password = u.password;
        this.email = u.email;
        this.role = u.role;
        this.registered_at = new Date();
        this.last_login = new Date();
        this.confirmed = u.confirmed;
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
    @XmlTransient
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

}
