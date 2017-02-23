package app.repository;

import app.model.Course;
import app.model.user.Lecturer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Ionut on 19-Dec-16.
 */
public interface LecturerRepository extends BaseRepository<Lecturer, Long>, PagingAndSortingRepository<Lecturer, Long> {

    List<Lecturer> findByFirstName(String firstName);

    List<Lecturer> findByLastName(String lastName);

    Page<Lecturer> findDistinctByFirstNameOrLastNameOrEmailAllIgnoreCaseContaining(String firstName, String lastName, String email, Pageable pageable);

    Lecturer findByEmail(String email);

    Page<Lecturer> findAllByCoursesId(Long id, Pageable pageable);

}
