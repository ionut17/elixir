package app.repository;

import app.model.user.User;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends BaseRepository<User>, Repository<User, Long> {

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    User findByEmail(String email);

    List<User> findByType(String type);

}
