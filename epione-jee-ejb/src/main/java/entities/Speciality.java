package entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement
public class Speciality implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@OneToMany(mappedBy="speciality", fetch = FetchType.LAZY)
	@JsonBackReference
	private Set<Reason> reasons=new HashSet<>();
	
	@OneToMany(mappedBy="speciality", fetch = FetchType.LAZY)
	@JsonBackReference
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
