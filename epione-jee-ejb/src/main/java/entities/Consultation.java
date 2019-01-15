package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.*;

@Entity
public class Consultation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+01")
	    private Date date_cons;
	private String remarks;
	
	private int rating;
	
	private double price;

	
	@OneToOne
	private Appointment appointment;

	public Consultation(){
		
	}
	
	public Consultation(int rating, double price) {
		super();
		this.rating=rating;
		this.price= price;
	}

	public Consultation(int id, String remarks, int rating, double price) {
		super();
		this.id = id;
		this.remarks = remarks;
		this.rating = rating;
		this.price = price;
	}

	public Consultation( String remarks, int rating, double price) {

		this.remarks = remarks;
		this.rating = rating;
		this.price = price;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
    @XmlTransient
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	

	
}
