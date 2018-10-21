package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Path implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Date date_path;
	
	private String description;
	
	@ManyToOne
	private Doctor doctor;
	
	@ManyToOne
	private Patient patient;
	
	@OneToMany(mappedBy="path")
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
	
	

}
