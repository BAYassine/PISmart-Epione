package interfaces;

import entities.Doctor;
import entities.Message;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface MessageServiceRemote {
	 /**
     * Author : Oumayma
     */
	  public int sendMessagePatient(int idP,int idD,Message msg);
	  public List<Message> PatientMessages(int idP, int nbMsg);

    /**
     * Author : Yassine
     */
    List<Message> inbox(Doctor doctor, int limit);

    /**
     * Author : Yassine
     */
    long unreadMessages(Doctor doctor);
}
