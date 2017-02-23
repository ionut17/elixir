package app.repository;

import app.model.Group;
import app.model.user.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface GroupRepository extends BaseRepository<Group, Long>, PagingAndSortingRepository<Group, Long> {

    Group findByName(String name);

    List<Group> findByYear(int year);

    Page<Group> findByStudents(Student student, Pageable pageable);

}
