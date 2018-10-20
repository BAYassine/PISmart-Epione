package entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Entity
public class Treatments implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String description;
	private String recomended_doc;
	@OneToOne
	private Appointments appointment;
	@ManyToOne
	private Path path;
	public Treatments() {

	}
	public Treatments(int id, String description, String recomended_doc, Appointments appointment) {
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
	public Appointments getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointments appointment) {
		this.appointment = appointment;
	}
	public Path getPath() {
		return path;
	}
	public void setPath(Path path) {
		this.path = path;
	}
	
	
}
