package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Message implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String content;
	private Date date_msg;
	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Doctor doctor;
	public Message() {
		
	}
	public Message(int id, String content, Date date_msg,Patient patient,Doctor doctor) {
		this.id = id;
		this.content = content;
		this.date_msg = date_msg;
		this.patient=patient;
		this.doctor=doctor;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate_msg() {
		return date_msg;
	}
	public void setDate_msg(Date date_msg) {
		this.date_msg = date_msg;
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
}
