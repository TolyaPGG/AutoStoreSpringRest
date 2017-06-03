package service.impl;

import form.UserRegistrationForm;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import service.UserService;
import util.UserRegistrationFormToUser;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveNewUser(UserRegistrationForm form) {
        User user = UserRegistrationFormToUser.transform(form);
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User confirmUser(long id, String token) {
        User currenUser = userRepository.findOneById(id);
        if(currenUser.getPassword().equals(token)){
            currenUser.setEmailConfirmed(true);
            userRepository.save(currenUser);
        }
        return currenUser;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = userRepository.findAll();
        return list;

    }

    @Override
    public void removeUser(long userId) {
        userRepository.delete(userId);
    }

    @Override
    public void changeUserConfirmatiion(long userId) {
        User user = userRepository.getOne(userId);
        user.setEmailConfirmed(!user.isEmailConfirmed());
        userRepository.save(user);
    }


}
