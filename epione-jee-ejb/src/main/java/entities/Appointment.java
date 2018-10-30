package entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@XmlRootElement
public class Appointment implements Serializable {

    public enum states { CANCELED, UPCOMING, ONGOING, DONE }
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+01")
    private Date date_start;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+01")
    private Date date_end;
    private String message;
    private states state;

    @OneToOne
	private Reason reason;
    
    @ManyToOne

    @JoinColumn(updatable=false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(updatable=false)
    private Doctor doctor;

    @OneToOne(mappedBy="appointment", fetch = FetchType.LAZY,cascade= CascadeType.REMOVE)
    private Consultation consultation;

    @OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY,cascade=CascadeType.REMOVE)
    private Treatment treatment;

    public Appointment() {

    }

    public Appointment(Date date_start, Reason reason, String msg) {
        this.date_start = date_start;
        this.reason = reason;
        this.message = msg;

    }

    public Appointment(Date date_start, Reason reason, String msg, Doctor doctor, Patient patient) {
        this.date_start = date_start;
        this.reason = reason;
        this.message = msg;
        this.doctor = doctor;
        this.patient = patient;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

  

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   
    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public states getState() {
        return state;
    }

    public void setState(states state) {
        this.state = state;
    }

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}
	
}
