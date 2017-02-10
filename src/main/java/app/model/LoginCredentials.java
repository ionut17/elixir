package app.model;

/**
 * Created by Ionut on 06-Feb-17.
 */
public class LoginCredentials {

    private String email;

    private String password;

    public LoginCredentials(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
