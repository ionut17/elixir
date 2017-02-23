package app.repository;

import app.model.Course;
import app.model.user.Lecturer;
import app.model.user.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CourseRepository extends BaseRepository<Course, Long>, PagingAndSortingRepository<Course, Long> {

    Course findByTitle(String title);

    List<Course> findByYear(int year);

    List<Course> findBySemester(int semester);

    Page<Course> findByStudents(Student student, Pageable pageable);

    Page<Course> findByLecturers(Lecturer lecturer, Pageable pageable);

}
