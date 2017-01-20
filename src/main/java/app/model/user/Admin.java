package app.model.user;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "ADMINS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Admin extends AbstractUser {

    public Admin(String password, String firstName, String lastName, String email) {
        super(password, firstName, lastName, email);
    }

    public Admin() {
    }

}
