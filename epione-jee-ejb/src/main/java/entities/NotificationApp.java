package entities;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class NotificationApp implements Serializable{
	
    public enum Confiramtion { YES, NO ,WAITING}
    private static final long serialVersionUID = 1L;
    
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Confiramtion confirmation;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date notified_at;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date new_Appointement_Date;
	
    @ManyToOne
    private Patient patientnotif;
    
    private String content;

    public NotificationApp() {
		// TODO Auto-generated constructor stub
	}
    
    
	public NotificationApp(Confiramtion confirmation, Date notified_at, Date new_Appointement_Date,
			Patient patientnotif) {
		super();
		this.confirmation = confirmation;
		this.notified_at = notified_at;
		this.new_Appointement_Date = new_Appointement_Date;
		this.patientnotif = patientnotif;
	}

	public NotificationApp(Date notified_at, Patient patientnotif, String content) {
		super();
		this.notified_at = notified_at;
		this.patientnotif = patientnotif;
		this.content = content;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Confiramtion getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(Confiramtion confirmation) {
		this.confirmation = confirmation;
	}

	public Date getNotified_at() {
		return notified_at;
	}

	public void setNotified_at(Date notified_at) {
		this.notified_at = notified_at;
	}

	public Date getNew_Appointement_Date() {
		return new_Appointement_Date;
	}
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setNew_Appointement_Date(Date new_Appointement_Date) {
		this.new_Appointement_Date = new_Appointement_Date;
	}

	public Patient getPatientnotif() {
		return patientnotif;
	}

	public void setPatientnotif(Patient patientnotif) {
		this.patientnotif = patientnotif;
	}

	@Override
	public String toString() {
		return "NotificationApp [id=" + id + ", confirmation=" + confirmation + ", notified_at=" + notified_at
				+ ", new_Appointement_Date=" + new_Appointement_Date + ", patientnotif=" + patientnotif + "]";
	}
    

}