package app.service.user;

import app.model.user.Lecturer;
import app.repository.LecturerRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("lecturerService")
public class LecturerServiceImpl implements LecturerService {

    //Main repository
    @Autowired
    private LecturerRepository lecturers;

    //Other dependencies
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Logger logger;

    public LecturerServiceImpl() {
    }

    @Override
    public List<Lecturer> findAll() {
        return lecturers.findAll();
    }

    @Override
    public Lecturer findById(long id) {
        return lecturers.findOne(id);
    }

    @Override
    public Lecturer add(Lecturer lecturer) {
        String encodedPassword = passwordEncoder.encode(lecturer.getPassword());
//        logger.info(encodedPassword);
        Lecturer newLecturer = new Lecturer(encodedPassword, lecturer.getFirstName(), lecturer.getLastName(), lecturer.getEmail());
        return lecturers.save(newLecturer);
    }

    @Override
    public Lecturer update(Lecturer lecturer) {
        return null;
    }

    @Override
    public void remove(Long id) {
        lecturers.delete(id);
    }

    @Override
    public boolean entityExist(Lecturer lecturer) {
        Lecturer found = lecturers.findByEmail(lecturer.getEmail());
        if (found == null) {
            return false;
        }
        return true;
    }

    @Override
    public Lecturer findByEmail(String email) {
        return lecturers.findByEmail(email);
    }

}
