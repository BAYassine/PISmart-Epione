package interfaces;

import entities.Patient;
import entities.User;

import javax.ejb.Local;
import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Local
public interface UserServiceLocal {

    User findUser(String username) throws NoResultException;
    int create(User u);
    int create(Patient u);
    void update(User u);
    void remove(User u);
    void updateLoginDate(User u);


    /**
     * Author : Yassine
     */
    long todayUsers();
    long registeredSince(String date);
    Map<String, Long> subscrtionsPerMonth();
    Map<Date, Long> connectionsPerDay();
    List<User> latestRegistrations(int limit);

    /**
     * Author : Fares
     */
	User check(String username, String password);
	User getAllAppointments(String username, String password);

}
