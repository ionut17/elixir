package app.repository;

import app.model.user.User;
import app.model.user.UserId;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends Repository<User, UserId> {

    List<User> findAll();

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    User findByEmail(String email);

//    List<User> findByType(String type);

}
