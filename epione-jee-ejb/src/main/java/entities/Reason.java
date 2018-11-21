package entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashSet;
import java.util.Set;
@Entity
@XmlRootElement
public class Reason {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@ManyToOne
	@JsonManagedReference
	private Speciality speciality;
	/*Fares*/
	@ManyToMany(mappedBy="reasons", fetch = FetchType.LAZY)
	@JsonBackReference
	private Set<Doctor> doctors=new HashSet<>();

	public Reason() {
		
	}

	public Reason(int id, String name) {
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
	@XmlTransient
	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality spaciality) {
		this.speciality = spaciality;
	}
	@XmlTransient
	public Set<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(Set<Doctor> doctors) {
		this.doctors = doctors;
	}

	public void addDoctor(Doctor s) {
		this.doctors.add(s);
		
	}




	

}
