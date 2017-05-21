package app.service.user;

import app.model.Course;
import app.model.Group;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityFile;
import app.model.activity.ActivityGrade;
import app.model.dto.CourseDto;
import app.model.dto.StudentDto;
import app.model.user.Lecturer;
import app.model.user.Student;
import app.model.user.User;
import app.repository.CourseRepository;
import app.repository.GroupRepository;
import app.repository.StudentRepository;
import app.repository.activity.ActivityAttendanceRepository;
import app.repository.activity.ActivityFileRepository;
import app.repository.activity.ActivityGradeRepository;
import app.service.AuthDetailsService;
import app.service.ParsingService;
import app.service.common.BaseService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.h2.result.SortOrder;
import org.hibernate.boot.model.source.spi.Sortable;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.w3c.dom.css.CSSValueList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component("studentService")
public class StudentServiceImpl implements StudentService {

    //Main repository
    @Autowired
    private StudentRepository students;

    //Other repositories
    @Autowired
    private ActivityAttendanceRepository activityAttendanceRepository;
    @Autowired
    private ActivityGradeRepository activityGradeRepository;
    @Autowired
    private ActivityFileRepository activityFileRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CourseRepository courseRepository;

    //Other dependencies
    @Autowired
    AuthDetailsService authDetailsService;
    @Autowired
    ParsingService parsingService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Logger logger;

    public StudentServiceImpl() {
    }

    @Override
    public List<StudentDto> findAll() {
        Type listType = new TypeToken<List<StudentDto>>() {}.getType();
        return modelMapper.map(students.findAll(), listType);
    }

    @Override
    public Page<StudentDto> findAllByPage(int page) {
        Type listType = new TypeToken<Page<StudentDto>>() {}.getType();
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                //Revoke access of student to list of students
                return null;
            case "lecturer":
            case "admin":
                return modelMapper.map(students.findAll(new PageRequest(page, 10, Sort.Direction.ASC, "lastName")), listType);
        }
        return null;
    }


    @Override
    public StudentDto findById(Long id) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                //Give access only to student's own page
                if (authenticatedUser.getId().equals(id)){
                    return modelMapper.map(students.findOne(id), StudentDto.class);
                }
                return null;
            case "lecturer":
            case "admin":
                Student stud = students.findOne(id);
                if (stud == null)
                    return null;
                else
                    return modelMapper.map(stud, StudentDto.class);
        }
        return modelMapper.map(students.findOne(id), StudentDto.class);
    }

    @Override
    public StudentDto add(StudentDto student) {
        String encodedPassword = passwordEncoder.encode(student.getPassword());
//        logger.info(encodedPassword);
        Student newStudent = new Student(encodedPassword, student.getFirstName(), student.getLastName(), student.getEmail());
        return modelMapper.map(students.save(newStudent), StudentDto.class);
    }

    @Override
    public StudentDto update(StudentDto student) {
        return null;
    }

    @Override
    public void remove(Long id) {
        students.delete(id);
    }

    @Override
    public boolean entityExist(StudentDto student) {
        Student found = students.findByEmail(student.getEmail());
        if (found == null) {
            return false;
        }
        return true;
    }

    @Override
    public StudentDto findByEmail(String email) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                //Give access only to student's own page
                if (authenticatedUser.getEmail().equals(email)){
                    return modelMapper.map(students.findByEmail(email), StudentDto.class);
                }
                return null;
            case "lecturer":
            case "admin":
                Student stud = students.findByEmail(email);
                if (stud != null){
                    return modelMapper.map(stud, StudentDto.class);
                }
                else return null;
        }
        return modelMapper.map(students.findByEmail(email), StudentDto.class);
    }

    @Override
    public List<StudentDto> findByCourseId(Long courseId) {
        Type listType = new TypeToken<List<StudentDto>>() {}.getType();
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                return null;
            case "lecturer":
            case "admin":
                return modelMapper.map(students.findByCoursesId(courseId, new Sort(Sort.Direction.ASC, "lastName")), listType);
        }
        return null;
    }

    @Override
    public Page<ActivityAttendance> getAttendances(StudentDto student, int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                //Give access only to student's own page
                if (authenticatedUser.getId().equals(student.getId())){
                    return activityAttendanceRepository.findByIdStudentId(student.getId(), new PageRequest(page, 10));
                }
                return null;
            case "lecturer":
            case "admin":
                return activityAttendanceRepository.findByIdStudentId(student.getId(), new PageRequest(page, 10));
        }
        return activityAttendanceRepository.findByIdStudentId(student.getId(), new PageRequest(page, 10));
    }

    @Override
    public Page<ActivityGrade> getGrades(StudentDto student, int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                //Give access only to student's own page
                if (authenticatedUser.getId().equals(student.getId())){
                    return activityGradeRepository.findByIdStudentId(student.getId(), new PageRequest(page, 10));
                }
                return null;
            case "lecturer":
            case "admin":
                return activityGradeRepository.findByIdStudentId(student.getId(), new PageRequest(page, 10));
        }
        return activityGradeRepository.findByIdStudentId(student.getId(), new PageRequest(page, 10));
    }

    @Override
    public Page<ActivityFile> getFiles(StudentDto student, int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                //Give access only to student's own page
                if (authenticatedUser.getId().equals(student.getId())){
                    return activityFileRepository.findByStudentId(student.getId(), new PageRequest(page, 10));
                }
                return null;
            case "lecturer":
            case "admin":
            return activityFileRepository.findByStudentId(student.getId(), new PageRequest(page, 10));
        }
        return activityFileRepository.findByStudentId(student.getId(), new PageRequest(page, 10));
    }

    @Override
    public Page<Group> getGroups(StudentDto student, int page) {
        Student found = students.findOne(student.getId());
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                //Give access only to student's own page
                if (authenticatedUser.getId().equals(student.getId())){
                    return groupRepository.findByStudents(found, new PageRequest(page, 10));
                }
                return null;
            case "lecturer":
            case "admin":
                return groupRepository.findByStudents(found, new PageRequest(page, 10));
        }
        return groupRepository.findByStudents(found, new PageRequest(page, 10));
    }

    @Override
    public Page<CourseDto> getCourses(StudentDto student, int page) {
        Type listType = new TypeToken<Page<CourseDto>>() {}.getType();
        Student found = students.findOne(student.getId());
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                //Give access only to student's own page
                if (authenticatedUser.getId().equals(student.getId())){
                    return modelMapper.map(courseRepository.findByStudents(found, new PageRequest(page, 10)), listType);
                }
                return null;
            case "lecturer":
            case "admin":
                return modelMapper.map(courseRepository.findByStudents(found, new PageRequest(page, 10)), listType);
        }
        return modelMapper.map(courseRepository.findByStudents(found, new PageRequest(page, 10)), listType);
    }

    @Override
    public Page<StudentDto> searchByPage(String query, int page) {
        Type listType = new TypeToken<Page<StudentDto>>() {}.getType();
        return modelMapper.map(students.findDistinctByFirstNameOrLastNameOrEmailAllIgnoreCaseContaining(query, query, query, new PageRequest(page, 10)), listType);
    }

    @Override
    public StudentDto addGroupToStudent(Group group, StudentDto studentDto) {
        Student student = students.findOne(studentDto.getId());
        return modelMapper.map(students.addGroupToStudent(group, student), StudentDto.class);
    }

    @Override
    public StudentDto removeGroupOfStudent(Group group, StudentDto student) {
        return modelMapper.map(students.removeGroupOfStudent(group, modelMapper.map(student, Student.class)), StudentDto.class);
    }

    @Override
    public List<StudentDto> importEntities(File file) throws IOException {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                return null;
            case "lecturer":
            case "admin":
                CSVParser parser = CSVParser.parse(file, StandardCharsets.UTF_8, CSVFormat.EXCEL.withIgnoreSurroundingSpaces().withHeader());
                String email;
                for (CSVRecord csvRecord : parser) {
                    email = csvRecord.get("EMAIL").trim();
                    if (this.findByEmail(email) == null){
                        StudentDto newStudent = new StudentDto();
                        Map<String, String> nameMap = parsingService.parseFirstLastName(csvRecord.get("NAME").trim());
                        newStudent.setFirstName(nameMap.get("firstName"));
                        newStudent.setLastName(nameMap.get("lastName"));
                        newStudent.setEmail(email);
                        newStudent.setPassword("qwe123");
                        this.add(newStudent);
                    }
                }
                return null;
        }
        return null;
    }

    @Override
    public StudentDto addCourseToStudent(Long courseId, Long studentId) {
        Student currentStudent = students.findOne(studentId);
        if (currentStudent == null){
            return null;
        }
        Course currentCourse = courseRepository.findOne(courseId);
        if (currentCourse == null){
            return null;
        }
        return modelMapper.map(students.addCourseToStudent(currentCourse, currentStudent), StudentDto.class);
    }

}
