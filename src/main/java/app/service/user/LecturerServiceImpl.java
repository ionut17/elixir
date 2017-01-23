package app.service.user;

import app.model.Course;
import app.model.dto.LecturerDto;
import app.model.user.Lecturer;
import app.repository.LecturerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
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
    private ModelMapper modelMapper;
    @Autowired
    private Logger logger;

    public LecturerServiceImpl() {
    }

    @Override
    public List<LecturerDto> findAll() {
        Type listType = new TypeToken<List<LecturerDto>>() {}.getType();
        return modelMapper.map(lecturers.findAll(), listType);
    }

    @Override
    public LecturerDto findById(Long id) {
        List<Course> courses = lecturers.findOne(id).getCourses();
        int x = 3;
        return modelMapper.map(lecturers.findOne(id),LecturerDto.class);
    }

    @Override
    public LecturerDto add(LecturerDto lecturer) {
        String encodedPassword = passwordEncoder.encode(lecturer.getPassword());
//        logger.info(encodedPassword);
        Lecturer newLecturer = new Lecturer(encodedPassword, lecturer.getFirstName(), lecturer.getLastName(), lecturer.getEmail());
        return modelMapper.map(lecturers.save(newLecturer), LecturerDto.class);
    }

    @Override
    public LecturerDto update(LecturerDto lecturer) {
        return null;
    }

    @Override
    public void remove(Long id) {
        lecturers.delete(id);
    }

    @Override
    public boolean entityExist(LecturerDto lecturer) {
        Lecturer found = lecturers.findByEmail(lecturer.getEmail());
        if (found == null) {
            return false;
        }
        return true;
    }

    @Override
    public LecturerDto findByEmail(String email) {
        return modelMapper.map(lecturers.findByEmail(email), LecturerDto.class);
    }

}
