package entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
@Entity
@XmlRootElement
public class Treatment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String description;
	
	private String recomended_doc;
	
	@OneToOne
	@JsonManagedReference
	private Appointment appointment;
	
	@ManyToOne
	@JsonManagedReference
	private Path path;
	
	public Treatment() {

	}
	public Treatment(int id, String description, String recomended_doc, Appointment appointment) {
		this.id = id;
		this.description = description;
		this.recomended_doc = recomended_doc;
		this.appointment = appointment;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRecomended_doc() {
		return recomended_doc;
	}
	public void setRecomended_doc(String recomended_doc) {
		this.recomended_doc = recomended_doc;
	}
	@XmlTransient
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	@JsonIgnore
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	
	
}
