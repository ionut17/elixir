package app.service.user;

import app.model.user.*;
import app.repository.StudentRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
    private Logger logger;

    public StudentServiceImpl() {
    }

    @Override
    public List<Student> findAll() {
        return students.findAll();
    }

    @Override
    public Student findById(long id) {
        return students.findOne(id);
    }

    @Override
    public Student add(Student student) {
        String encodedPassword = passwordEncoder.encode(student.getPassword());
//        logger.info(encodedPassword);
        Student newStudent = new Student(encodedPassword, student.getFirstName(), student.getLastName(), student.getEmail());
        return students.save(newStudent);
    }

    @Override
    public Student update(Student student) {
        return null;
    }

    @Override
    public void remove(Long id) {
        students.delete(id);
    }

    @Override
    public boolean entityExist(Student student) {
        Student found = students.findByEmail(student.getEmail());
        if (found == null) {
            return false;
        }
        return true;
    }

    @Override
    public Student findByEmail(String email) {
        return students.findByEmail(email);
    }

}
