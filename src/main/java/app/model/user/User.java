package app.model.user;

import app.model.common.Item;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;

@Primary
@Entity
@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public final class User implements Item, Serializable {

    //Fields

    @Id
    private String v_id;

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "email", nullable = false, length = 30, unique = true)
    private String email;

    @Column(name = "type", nullable = false)
    private String type;

    //Constructors

    public User(String type, String password, String firstName, String lastName, String email) {
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setType(type);
    }

    public User() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
