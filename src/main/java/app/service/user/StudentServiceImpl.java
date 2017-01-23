package app.service.user;

import app.model.Group;
import app.model.dto.StudentDto;
import app.model.user.Student;
import app.repository.StudentRepository;
import app.service.common.BaseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component("studentService")
public class StudentServiceImpl implements StudentService {

    //Main repository
    @Autowired
    private StudentRepository students;

    //Other dependencies
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
    public StudentDto findById(Long id) {
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
        return modelMapper.map(students.findByEmail(email), StudentDto.class);
    }

    @Override
    public StudentDto addGroupToStudent(Group group, StudentDto student) {
        return modelMapper.map(students.addGroupToStudent(group, modelMapper.map(student, Student.class)), StudentDto.class);
    }

    @Override
    public StudentDto removeGroupOfStudent(Group group, StudentDto student) {
        return modelMapper.map(students.removeGroupOfStudent(group, modelMapper.map(student, Student.class)), StudentDto.class);
    }

}
