package entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Event implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Eventid;
	
	private String Subject;
	private String Description;
	
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+01")
	    private Date Start;
	 
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+01")
	    private Date End;
	 
	 private String ThemeColor;
	 
	 private boolean IsFullDay;

	public int getEventid() {
		return Eventid;
	}

	public void setEventid(int eventid) {
		Eventid = eventid;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Date getStart() {
		return Start;
	}

	public void setStart(Date start) {
		Start = start;
	}

	public Date getEnd() {
		return End;
	}

	public void setEnd(Date end) {
		End = end;
	}

	public String getThemeColor() {
		return ThemeColor;
	}

	public void setThemeColor(String themeColor) {
		ThemeColor = themeColor;
	}

	public boolean isIsFullDay() {
		return IsFullDay;
	}

	public void setIsFullDay(boolean isFullDay) {
		IsFullDay = isFullDay;
	}
}
