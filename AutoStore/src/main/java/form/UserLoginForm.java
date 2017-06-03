package form;

import org.hibernate.validator.constraints.NotEmpty;


public class UserLoginForm {
    @NotEmpty(message = "not empty field")
    private String username;

    @NotEmpty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty
    private String password;
}
