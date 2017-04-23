package app.service.user;

import app.model.Course;
import app.model.dto.CourseDto;
import app.model.dto.LecturerDto;
import app.model.user.Lecturer;
import app.repository.CourseRepository;
import app.repository.LecturerRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component("lecturerService")
public class LecturerServiceImpl implements LecturerService {

    //Main repository
    @Autowired
    private LecturerRepository lecturers;

    //Other repositories
    @Autowired
    private CourseRepository courseRepository;

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
    public Page<LecturerDto> findAllByPage(int page) {
        Type listType = new TypeToken<Page<LecturerDto>>() {}.getType();
        return modelMapper.map(lecturers.findAll(new PageRequest(page, 10)), listType);
    }

    @Override
    public LecturerDto findById(Long id) {
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

    @Override
    public Page<CourseDto> getCourses(LecturerDto lecturer, int page){
        Type listType = new TypeToken<Page<CourseDto>>() {}.getType();
        Lecturer found = lecturers.findOne(lecturer.getId());
        return modelMapper.map(courseRepository.findByLecturers(found, new PageRequest(page, 10)), listType);
    }

    @Override
    public LecturerDto addCourseToLecturer(Long courseId, Long lecturerId) {
        Course course = courseRepository.findOne(courseId);
        Lecturer lecturer = lecturers.findOne(lecturerId);
        return modelMapper.map(lecturers.addCourseToLecturer(course, lecturer), LecturerDto.class);
    }

    @Override
    public LecturerDto removeCourseOfLecturer(Long courseId, Long lecturerId) {
        Course course = courseRepository.findOne(courseId);
        Lecturer lecturer = lecturers.findOne(lecturerId);
        return modelMapper.map(lecturers.removeCourseOfLecturer(course, lecturer), LecturerDto.class);
    }

    @Override
    public Page<LecturerDto> searchByPage(String query, int page) {
        Type listType = new TypeToken<Page<LecturerDto>>() {}.getType();
        return modelMapper.map(lecturers.findDistinctByFirstNameOrLastNameOrEmailAllIgnoreCaseContaining(query, query, query, new PageRequest(page, 10)), listType);
    }

}
