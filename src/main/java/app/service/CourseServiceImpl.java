package app.service;

import app.auth.JwtAuthenticatedUser;
import app.model.Course;
import app.model.activity.Activity;
import app.model.dto.CourseDto;
import app.model.dto.LecturerDto;
import app.model.dto.StudentDto;
import app.model.user.Lecturer;
import app.model.user.Student;
import app.model.user.User;
import app.repository.CourseRepository;
import app.repository.LecturerRepository;
import app.repository.StudentRepository;
import app.repository.activity.ActivityRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
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
    @Autowired
    ActivityRepository activities;
    @Autowired
    private ModelMapper modelMapper;

    public CourseServiceImpl() {

    }

    @Override
    public List<CourseDto> findAll() {
        Type listType = new TypeToken<List<CourseDto>>() {}.getType();
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                Student student = students.findOne(authenticatedUser.getId());
                return modelMapper.map(student.getCourses(), listType);
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                return modelMapper.map(lecturer.getCourses(), listType);
            case "admin":
                return modelMapper.map(courses.findAll(), listType);
        }
        return modelMapper.map(courses.findAll(), listType);
    }

    @Override
    public Page<CourseDto> findAllByPage(int page) {
        Type listType = new TypeToken<Page<CourseDto>>() {}.getType();
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                Student student = students.findOne(authenticatedUser.getId());
                return modelMapper.map(courses.findByStudents(student, new PageRequest(page, 10)), listType);
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                return modelMapper.map(courses.findByLecturers(lecturer, new PageRequest(page, 10)), listType);
            case "admin":
                return modelMapper.map(courses.findAll(new PageRequest(page, 10)), listType);
        }
        return modelMapper.map(courses.findAll(new PageRequest(page, 10)), listType);
    }

    @Override
    public CourseDto findById(Long id) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                Student student = students.findOne(authenticatedUser.getId());
                Course course = courses.findOne(id);
                //Give access only to his courses
                if (student.getCourses().contains(course)){
                    course.setStudents(null);
                    course.setActivities(null);
                    return modelMapper.map(course, CourseDto.class);
                }
                return null;
            case "lecturer":
            case "admin":
                return modelMapper.map(courses.findOne(id), CourseDto.class);
        }
        return modelMapper.map(courses.findOne(id), CourseDto.class);
    }

    @Override
    public List<CourseDto> findByYear(int year) {
        Type listType = new TypeToken<List<CourseDto>>() {}.getType();
        return modelMapper.map(courses.findByYear(year), listType);
    }

    @Override
    public List<CourseDto> findBySemester(int semester) {
        Type listType = new TypeToken<List<CourseDto>>() {}.getType();
        return modelMapper.map(courses.findBySemester(semester), listType);
    }

    @Override
    public Page<LecturerDto> getLecturers(CourseDto course, int page) {
        Type listType = new TypeToken<Page<LecturerDto>>() {}.getType();
        return modelMapper.map(lecturers.findAllByCoursesId(course.getId(), new PageRequest(page, 10)), listType);
    }

    @Override
    public Page<StudentDto> getStudents(CourseDto course, int page) {
        Type listType = new TypeToken<Page<StudentDto>>() {}.getType();
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                return null;
            case "lecturer":
            case "admin":
                return modelMapper.map(students.findByCoursesId(course.getId(), new PageRequest(page, 10)), listType);
        }
        return modelMapper.map(students.findByCoursesId(course.getId(), new PageRequest(page, 10)), listType);
    }

    @Override
    public Page<Activity> getActivities(CourseDto course, int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                return null;
            case "lecturer":
            case "admin":
                return activities.findByCourseId(course.getId(), new PageRequest(page, 10));
        }
        return activities.findByCourseId(course.getId(), new PageRequest(page, 10));
    }

    @Override
    public CourseDto findByTitle(String title) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                Student student = students.findOne(authenticatedUser.getId());
                Course course = courses.findByTitle(title);
                //Give access only to his courses
                if (student.getCourses().contains(course)){
                    return modelMapper.map(course, CourseDto.class);
                }
                return null;
            case "lecturer":
            case "admin":
              return modelMapper.map(courses.findByTitle(title), CourseDto.class);
        }
        return modelMapper.map(courses.findByTitle(title), CourseDto.class);
    }

    @Override
    public CourseDto add(CourseDto entity) {
        return modelMapper.map(courses.save(modelMapper.map(entity, Course.class)), CourseDto.class);
    }

    @Override
    public CourseDto update(CourseDto user) {
        return null;
    }

    @Override
    public void remove(Long id) {
        courses.delete(id);
    }

    @Override
    public boolean entityExist(CourseDto entity) {
        Course found = courses.findByTitle(entity.getTitle());
        if (found == null) {
            return false;
        }
        return true;
    }

}
