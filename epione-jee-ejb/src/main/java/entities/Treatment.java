package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@XmlRootElement
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
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
	private Appointment appointment;
	
	@ManyToOne
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
