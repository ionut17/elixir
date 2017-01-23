package app.repository;

import app.model.Course;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CourseRepository extends BaseRepository<Course, Long>, Repository<Course, Long> {

    Course findByTitle(String title);

    List<Course> findByYear(int year);

    List<Course> findBySemester(int semester);

}
