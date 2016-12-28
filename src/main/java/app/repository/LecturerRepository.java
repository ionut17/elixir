package app.repository;

import app.model.user.Lecturer;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Ionut on 19-Dec-16.
 */
public interface LecturerRepository extends BaseRepository<Lecturer>, Repository<Lecturer, Long> {

    List<Lecturer> findByFirstName(String firstName);

    List<Lecturer> findByLastName(String lastName);

    Lecturer findByEmail(String email);

}
