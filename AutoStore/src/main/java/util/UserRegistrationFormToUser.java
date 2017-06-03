package util;

import form.UserRegistrationForm;
import model.User;
import model.enums.UserRoles;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserRegistrationFormToUser {
    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static User transform(UserRegistrationForm form) {
        if (form == null) {
            return null;
        }
        User user = new User();
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setEmailConfirmed(false);
        user.setRole(UserRoles.ROLE_USER);
        user.setPassword(encoder.encode(form.getPassword()));
        return user;
    }
}
