package interfaces;

import entities.Doctor;
import entities.Message;

import javax.ejb.Local;
import java.util.List;

@Local
public interface MessageServiceLocal {

    /**
     * Author : Yassine
     */
    List<Message> inbox(Doctor doctor, int limit);
}
