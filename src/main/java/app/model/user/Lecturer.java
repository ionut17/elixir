package app.model.user;

import javax.persistence.*;

@Entity
@Table(name = "LECTURERS")
public class Lecturer extends AbstractUser {

    public Lecturer(String password, String firstName, String lastName, String email){
        super(password, firstName, lastName, email);
    }

    public Lecturer(){
    }

}
