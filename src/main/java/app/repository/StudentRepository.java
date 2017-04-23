package app.repository;

import app.model.Course;
import app.model.user.Student;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Ionut on 19-Dec-16.
 */
public interface StudentRepository extends BaseRepository<Student, Long>, PagingAndSortingRepository<Student, Long>, StudentRepositoryCustom {

    List<Student> findByFirstName(String firstName);

    List<Student> findByLastName(String lastName);

    Page<Student> findDistinctByFirstNameOrLastNameOrEmailAllIgnoreCaseContaining(String firstName, String lastName, String email, Pageable pageable);

    Student findByEmail(String email);

    List<Student> findByCoursesId(Long id, Sort sortable);

    Page<Student> findByCoursesId(Long id, Pageable pageable);

}
