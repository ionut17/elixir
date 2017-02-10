package app.service;

import app.auth.JwtAuthenticatedUser;
import app.model.Course;
import app.model.user.Lecturer;
import app.model.user.Student;
import app.model.user.User;
import app.repository.CourseRepository;
import app.repository.LecturerRepository;
import app.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    AuthDetailsService authDetailsService;
    @Autowired
    CourseRepository courses;
    @Autowired
    StudentRepository students;
    @Autowired
    LecturerRepository lecturers;

    public CourseServiceImpl() {

    }

    @Override
    public List<Course> findAll() {
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                Student student = students.findOne(authenticatedUser.getId());
                return student.getCourses();
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                return lecturer.getCourses();
            case "admin":
                return courses.findAll();
        }
        return courses.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courses.findOne(id);
    }

    @Override
    public List<Course> findByYear(int year) {
        return courses.findByYear(year);
    }

    @Override
    public List<Course> findBySemester(int semester) {
        return courses.findBySemester(semester);
    }

    @Override
    public Course findByTitle(String title) {
        return courses.findByTitle(title);
    }

    @Override
    public Course add(Course entity) {
        return courses.save(entity);
    }

    @Override
    public Course update(Course user) {
        return null;
    }

    @Override
    public void remove(Long id) {
        courses.delete(id);
    }

    @Override
    public boolean entityExist(Course entity) {
        Course found = courses.findByTitle(entity.getTitle());
        if (found == null) {
            return false;
        }
        return true;
    }

}
