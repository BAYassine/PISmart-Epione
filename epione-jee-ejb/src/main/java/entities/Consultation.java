package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Consultation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private Date date_cons;
	
	private String remarks;
	
	private int rating;
	
	private double price;
	
	@OneToOne
	private Report report;
	
	@OneToOne(mappedBy="consultation")
	private Appointment appointment;
	
	public Consultation(){
		
	}

	public Consultation(int id, Date date_cons, String remarks, int rating, double price, Report report,
			Appointment appointment) {
		this.id = id;
		this.date_cons = date_cons;
		this.remarks = remarks;
		this.rating = rating;
		this.price = price;
		this.report = report;
		this.appointment = appointment;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_cons() {
		return date_cons;
	}
	public void setDate_cons(Date date_cons) {
		this.date_cons = date_cons;
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
	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	
	
}
