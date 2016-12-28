package app.model.user;

import javax.persistence.*;

@Entity
@Table(name = "ADMINS")
public class Admin extends AbstractUser {

    public Admin(String password, String firstName, String lastName, String email){
        super(password, firstName, lastName, email);
    }

    public Admin(){
    }

}
