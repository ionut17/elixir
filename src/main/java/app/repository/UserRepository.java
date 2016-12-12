package app.repository;

import app.model.User;
import org.springframework.data.repository.Repository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserRepository extends Repository<User, Long> {

    List<User> findAll();

    User findOne(Long id);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    User findByEmail(String email);

    List<User> findByType(String type);

    User save(User persisted);

    void delete(Long id);

}
