package app.repository;

import app.model.Group;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface GroupRepository extends Repository<Group,Long> {

    List<Group> findAll();

    Group findOne(Long id);

    Group save(Group persisted);

    void delete(Long id);

}
