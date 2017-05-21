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
import app.service.user.LecturerService;
import app.service.user.StudentService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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
    LecturerService lecturerService;
    @Autowired
    StudentService studentService;
    @Autowired
    ActivityRepository activities;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ParsingService parsingService;

    private final Sort courseSort = new Sort(
            new Sort.Order(Sort.Direction.ASC, "year"),
            new Sort.Order(Sort.Direction.ASC, "semester"),
            new Sort.Order(Sort.Direction.ASC, "title")
    );

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
                return modelMapper.map(courses.findByStudents(student, new PageRequest(page, 10, courseSort)), listType);
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                return modelMapper.map(courses.findByLecturers(lecturer, new PageRequest(page, 10, courseSort)), listType);
            case "admin":
                return modelMapper.map(courses.findAll(new PageRequest(page, 10, courseSort)), listType);
        }
        return null;
    }

    @Override
    public Page<CourseDto> searchByPage(String query, int page) {
        Type listType = new TypeToken<Page<CourseDto>>() {}.getType();
        return modelMapper.map(courses.findDistinctByTitleAllIgnoreCaseContaining(query, new PageRequest(page, 10)), listType);
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
                Student student = students.findOne(authenticatedUser.getId());
                Course course1 = courses.findOne(course.getId());
                if (student.getCourses().contains(course1)){
                    return activities.findByCourseId(course.getId(), new PageRequest(page, 10, Sort.Direction.DESC, "date"));
                } else{
                    return null;
                }
            case "lecturer":
            case "admin":
                return activities.findByCourseId(course.getId(), new PageRequest(page, 10, Sort.Direction.DESC, "date"));
        }
        return activities.findByCourseId(course.getId(), new PageRequest(page, 10, Sort.Direction.DESC, "date"));
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
                Course result = courses.findByTitle(title);
                if (result == null){
                    return null;
                } else{
                    return modelMapper.map(result, CourseDto.class);
                }
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

    @Override
    public List<CourseDto> importEntities(File file) throws IOException {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
            case "lecturer":
                return null;
            case "admin":
                CSVParser parser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.EXCEL.withIgnoreSurroundingSpaces().withHeader());
                String title;
                for (CSVRecord csvRecord : parser) {
                    title = csvRecord.get("TITLE").trim();
                    if (this.findByTitle(title) == null){
                        CourseDto newCourse = new CourseDto();
                        newCourse.setTitle(title);
                        newCourse.setYear(Integer.valueOf(csvRecord.get("YEAR")));
                        newCourse.setSemester(Integer.valueOf(csvRecord.get("SEMESTER")));
                        this.add(newCourse);
                    }
                }
                return null;
        }
        return null;
    }

    @Override
    public void importCourseOwnerships(File file) throws IOException {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "lecturer":
            case "admin":
                CSVParser parser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.EXCEL.withIgnoreSurroundingSpaces().withHeader());
                CourseDto course;
                LecturerDto lecturer;
                for (CSVRecord csvRecord : parser) {
                    course = this.findByTitle(csvRecord.get("COURSE_TITLE").trim());
                    lecturer = lecturerService.findByEmail(csvRecord.get("LECTURER_EMAIL").trim());
                    if (course != null && lecturer != null){
                        lecturerService.addCourseToLecturer(course.getId(), lecturer.getId());
                    }
                }
        }
    }

    @Override
    public void importCourseAttendants(File file) throws IOException {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "lecturer":
            case "admin":
                CSVParser parser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.EXCEL.withIgnoreSurroundingSpaces().withHeader());
                CourseDto course;
                StudentDto student;
                for (CSVRecord csvRecord : parser) {
                    course = this.findByTitle(csvRecord.get("COURSE_TITLE").trim());
                    student = studentService.findByEmail(csvRecord.get("STUDENT_EMAIL").trim());
                    if (course != null && student != null){
                        studentService.addCourseToStudent(course.getId(), student.getId());
                    }
                }
        }
    }

}
