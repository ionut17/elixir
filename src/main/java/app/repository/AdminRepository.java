package app.repository;

import app.model.user.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Ionut on 19-Dec-16.
 */
public interface AdminRepository extends BaseRepository<Admin, Long>, PagingAndSortingRepository<Admin, Long> {

    List<Admin> findByFirstName(String firstName);

    List<Admin> findByLastName(String lastName);

    Page<Admin> findDistinctByFirstNameOrLastNameOrEmailAllIgnoreCaseContaining(String firstName, String lastName, String email, Pageable pageable);

    Admin findByEmail(String email);

}
