package app.repository;

import app.model.user.User;
import app.model.user.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface UserRepository extends PagingAndSortingRepository<User, UserId> {

//    List<User> findAll();

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);

    Page<User> findDistinctByFirstNameOrLastNameOrEmailAllIgnoreCaseContaining(String firstName, String lastName, String email, Pageable pageable);

    User findByEmail(String email);

//    List<User> findByType(String type);

}
