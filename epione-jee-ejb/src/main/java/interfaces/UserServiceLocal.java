package interfaces;

import entities.User;

import javax.ejb.Local;
import javax.ejb.LocalBean;

@Local
public interface UserServiceLocal {

    User findUser(String username);

    int create(User u);
}
