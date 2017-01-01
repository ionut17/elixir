package app.model.user;

import javax.persistence.*;

@Entity
@Table(name = "LECTURERS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Lecturer extends AbstractUser {

    public Lecturer(String password, String firstName, String lastName, String email){
        super(password, firstName, lastName, email);
    }

    public Lecturer(){
    }

}
