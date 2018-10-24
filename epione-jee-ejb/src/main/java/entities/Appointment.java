package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Appointment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	public enum Concern{me,relative,children};
	public enum Gender{male,female};
	
	private Date date_app;
	
	private String reason;
	private String message;	
	private boolean canceled=false;
	private Concern concerned;
	private Gender gender;
	private String name;
	private Date date_birth;
	private String address;
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
	public Appointment( Date date_app, String reason,String msg,Concern concerned,Gender gender,String name,Date dateb,String address) {
		this.date_app = date_app;
		this.reason = reason;
		this.message=msg;
		this.concerned=concerned;
		this.gender=gender;
		this.name=name;
		this.date_birth=dateb;
		this.address=address;
	}
	public Appointment( Date date_app, String reason,String msg ,Doctor doctor,Patient patient) {
		this.date_app = date_app;
		this.reason = reason;
		this.message=msg;
		this.doctor=doctor;
		this.patient=patient;
	}
	
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
