package service;

import form.UserRegistrationForm;
import model.User;

import java.util.List;


public interface UserService {

    User saveNewUser(UserRegistrationForm form);

    User confirmUser(long id, String token);

    List<User> getAllUsers();

    void removeUser(long userId);

    void changeUserConfirmatiion(long userId);
}
