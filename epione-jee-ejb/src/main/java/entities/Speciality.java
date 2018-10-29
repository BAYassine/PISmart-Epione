package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@XmlRootElement
public class Speciality implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@OneToMany(mappedBy="speciality", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Reason> reasons=new HashSet<>();
	
	@OneToMany(mappedBy="speciality", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Doctor> doctors=new HashSet<>();
	
	
	public Speciality(){
		
	}
	
	public Speciality(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Reason> getReasons() {
		return reasons;
	}

	public void setReasons(Set<Reason> reasons) {
		this.reasons = reasons;
	}

	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}


	

}
