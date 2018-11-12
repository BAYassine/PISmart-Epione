package entities;

public class ChatMessage {
	
	private String content;
	private String doctorName;
	private String patientName;
	private String isPatient;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getIsPatient() {
		return isPatient;
	}

	public void setIsPatient(String isPatient) {
		this.isPatient = isPatient;
	}

	@Override
	public String toString() {
		return "ChatMessage [content=" + content + ", doctorName=" + doctorName + ", patientName=" + patientName
				+ ", isPatient=" + isPatient + "]";
	}
	
	

}
