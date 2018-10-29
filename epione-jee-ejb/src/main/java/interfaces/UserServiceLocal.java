package interfaces;

import entities.User;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.persistence.NoResultException;

@Local
public interface UserServiceLocal {

    User findUser(String username) throws NoResultException;
    int create(User u);
    void update(User u);
    void remove(User u);
    void updateLoginDate(User u);


    /**
     * Author : Yassine
     */
    long todayUsers();

    long registeredSince(String date);
}
