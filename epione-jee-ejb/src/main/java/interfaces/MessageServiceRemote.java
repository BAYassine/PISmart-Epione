package interfaces;

import entities.Doctor;
import entities.Message;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface MessageServiceRemote {

    /**
     * Author : Yassine
     */
    List<Message> inbox(Doctor doctor, int limit);
}
