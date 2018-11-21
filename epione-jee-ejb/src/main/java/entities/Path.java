package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.*;

@Entity
public class Path implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date_path;
	
	private String description;
	
	@ManyToOne
	@JsonManagedReference
	private Doctor doctor;

	@ManyToOne
	@JsonManagedReference
	private Patient patient;
	
	
	@OneToMany(fetch = FetchType.LAZY ,mappedBy="path", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Treatment> list_treat;
	
	public Path() {
	
	}
	public Path(int id, Date date_path, String description, List<Treatment> list_treat) {
		super();
		this.id = id;
		this.date_path = date_path;
		this.description = description;
		this.list_treat = list_treat;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_path() {
		return date_path;
	}
	public void setDate_path(Date date_path) {
		this.date_path = date_path;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Treatment> getList_treat() {
		return list_treat;
	}
	public void setList_treat(List<Treatment> list_treat) {
		this.list_treat = list_treat;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	

}
