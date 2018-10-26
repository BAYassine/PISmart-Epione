package entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@XmlRootElement
public class Appointment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	private Date date_app;
	
	private String reason;
	private String message;	
	private boolean canceled=false;
	
	@ManyToOne

	private Patient patient;
	
	@ManyToOne

	private Doctor doctor;
	
	
	@OneToOne
	private Consultation consultation;
	
	@OneToOne(mappedBy="appointment")
	private Treatment treatment;
	
	public Appointment(){
		
	}
	public Appointment( Date date_app, String reason,String msg) {
		this.date_app = date_app;
		this.reason = reason;
		this.message=msg;
		
	}
	public Appointment( Date date_app, String reason,String msg ,Doctor doctor,Patient patient) {
		this.date_app = date_app;
		this.reason = reason;
		this.message=msg;
		this.doctor=doctor;
		this.patient=patient;
	}
	@XmlAttribute
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_app() {
		return date_app;
	}
	public void setDate_app(Date date_app) {
		this.date_app = date_app;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@XmlTransient
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
	public boolean isCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}


	
	
}
