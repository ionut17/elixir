package app.model.user;

import javax.persistence.*;

@Entity
@Table(name = "STUDENTS")
public class Student extends AbstractUser {

    public Student(String password, String firstName, String lastName, String email){
        super(password, firstName, lastName, email);
    }

    public Student(){
    }

}
