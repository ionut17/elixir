package app.model.user;

import javax.persistence.*;

/**
 * Created by Ionut on 19-Dec-16.
 */
@MappedSuperclass
public abstract class AbstractUser implements IUser {

    //Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "email", nullable = false, length = 30, unique = true)
    private String email;

    //Constructors

    public AbstractUser(String password, String firstName, String lastName, String email) {
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
    }

    public AbstractUser(){
    }

    //Setters and getters

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
            this.email = email;
        }

}
