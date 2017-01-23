package app.repository;

import app.model.Group;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface GroupRepository extends BaseRepository<Group, Long>, Repository<Group, Long> {

    Group findByName(String name);

    List<Group> findByYear(int year);

}
