package app.model.dto;

import app.model.Course;
import app.model.Group;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityGrade;
import app.model.common.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class UserDto implements Item {

    private Long id;

    private String type;

    @JsonIgnore
    private String password;

    private String firstName;

    private String lastName;

    private String email;

    //Constructors

    public UserDto() {
    }

    //Setters and getters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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
