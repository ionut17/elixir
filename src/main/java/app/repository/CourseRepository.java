package app.repository;

import app.model.Course;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CourseRepository extends Repository<Course, Long> {

    List<Course> findAll();

    Course findOne(Long id);

    Course findByTitle(String title);

    List<Course> findByYear(int year);

    List<Course> findBySemester(int semester);

    Course save(Course persisted);

    void delete(Long id);

}
