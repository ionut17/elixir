package app.repository;

import app.model.user.Admin;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Created by Ionut on 19-Dec-16.
 */
public interface AdminRepository extends BaseRepository<Admin, Long>, Repository<Admin, Long> {

    List<Admin> findByFirstName(String firstName);

    List<Admin> findByLastName(String lastName);

    Admin findByEmail(String email);

}
