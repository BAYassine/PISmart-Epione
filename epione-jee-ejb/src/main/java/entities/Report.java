package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.*;

@Entity
public class Report  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date_rep;
	
	@Column(length = 1000)
	private String content;
	
	private String pathFile;
	
	@OneToOne
	@JsonManagedReference
	private Consultation consultation;
	
	@OneToOne
	@JsonIgnore
	private Patient patient ;
	
	
	public Report(){
		
	}
	
	
	public Report(int id, Date date_rep, String content, String pathFile, Consultation consultation) {
		super();
		this.id = id;
		this.date_rep = date_rep;
		this.content = content;
		this.pathFile = pathFile;
		this.consultation = consultation;
	}


	public String getPathFile() {
		return pathFile;
	}
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_rep() {
		return date_rep;
	}
	public void setDate_rep(Date date_rep) {
		this.date_rep = date_rep;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@XmlTransient
	public Consultation getConsultation() {
		return consultation;
	}
	public void setConsultation(Consultation consultation) {
		this.consultation = consultation;
	}


	public Patient getPatient() {
		return patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	

}
