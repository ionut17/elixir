package app.repository;

import app.model.Group;
import app.model.user.Student;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Ionut on 19-Dec-16.
 */
public interface StudentRepository extends BaseRepository<Student>, Repository<Student, Long>, StudentRepositoryCustom {

    List<Student> findByFirstName(String firstName);

    List<Student> findByLastName(String lastName);

    Student findByEmail(String email);

}
