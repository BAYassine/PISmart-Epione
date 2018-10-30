package interfaces;

import entities.Doctor;
import entities.Message;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MessageServiceLocal {
	/**
     * Author : Oumayma
     */
	 public int sendMessagePatient(int idP,int idD,Message msg);
     public List<Message> PatientMessages(int idP, int nbMsg);
    /**
     * Author : Yassine
     */
    List<Message> inbox(Doctor doctor, int limit);
}
