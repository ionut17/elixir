package app.repository;

import app.model.User;
import org.springframework.data.repository.Repository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface UserRepository extends Repository<User,Long> {

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    List<User> findAll();

    User findOne(Long id);

    @PreAuthorize("hasRole('ROLE_LECTURER') or hasRole('ROLE_ADMIN')")
    List<User> findByType(String type);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    User findByEmail(String email);

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    User save(User persisted);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(Long id);

}
