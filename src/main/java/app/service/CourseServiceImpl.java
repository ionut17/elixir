package app.service;

import app.model.Course;
import app.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("courseService")
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courses;

    public CourseServiceImpl() {

    }

    @Override
    public List<Course> findAll() {
        return courses.findAll();
    }

    @Override
    public Course findById(long id) {
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
