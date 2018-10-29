package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement
@PrimaryKeyJoinColumn(name = "id")
public class Doctor extends User implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String presentation;
    private String location;
    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private Set<Availability> availabilities = new HashSet<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private Set<Path> paths = new HashSet<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    private Set<Message> messages = new HashSet<>();

    @ManyToOne
    private Speciality speciality;

    public Doctor() {
        super();
    }

    public Doctor(Speciality speciality, String location) {
        this.speciality = speciality;
        this.location = location;
    }

    @XmlTransient
    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(Set<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Path> getPaths() {
        return paths;
    }

    public void setPaths(Set<Path> paths) {
        this.paths = paths;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public void copy(Doctor d) {
        super.copy(d);
        if (this.speciality == null && d.speciality != null)
            this.speciality = d.speciality;
        if (this.location == null && d.location != null)
            this.location = d.location;
    }
}
