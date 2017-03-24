package app.service;

import app.model.Group;
import app.model.dto.CourseDto;
import app.model.user.Lecturer;
import app.model.user.Student;
import app.model.user.User;
import app.repository.GroupRepository;
import app.repository.LecturerRepository;
import app.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component("groupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    AuthDetailsService authDetailsService;
    @Autowired
    GroupRepository groups;
    @Autowired
    StudentRepository students;
    @Autowired
    private ModelMapper modelMapper;

    public GroupServiceImpl() {

    }

    @Override
    public List<Group> findAll() {
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                Student student = students.findOne(authenticatedUser.getId());
                return student.getGroups();
            case "lecturer":
            case "admin":
                return groups.findAll();
        }
        return groups.findAll();
    }

    @Override
    public Page<Group> findAllByPage(int page) {
        Type listType = new TypeToken<Page<Group>>() {}.getType();
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                Student student = students.findOne(authenticatedUser.getId());
                return modelMapper.map(groups.findByStudents(student, new PageRequest(page, 10)), listType);
            case "lecturer":
            case "admin":
                return groups.findAll(new PageRequest(page, 10));
        }
        return groups.findAll(new PageRequest(page, 10));
    }

    @Override
    public Page<Group> searchByPage(String query, int page) {
        return groups.findDistinctByNameAllIgnoreCaseContaining(query, new PageRequest(page, 10));
    }

    @Override
    public Group findById(Long id) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()){
            case "student":
                //Student can't view groups composition
                return null;
            case "lecturer":
            case "admin":
                return groups.findOne(id);
        }
        return groups.findOne(id);
    }

    @Override
    public List<Group> findByYear(int year) {
        return groups.findByYear(year);
    }

    @Override
    public Group findByName(String name) {
        return groups.findByName(name);
    }

    @Override
    public Group add(Group entity) {
        return groups.save(entity);
    }

    @Override
    public Group update(Group user) {
        return null;
    }

    @Override
    public void remove(Long id) {
        groups.delete(id);
    }

    @Override
    public boolean entityExist(Group entity) {
        Group found = groups.findByName(entity.getName());
        if (found == null) {
            return false;
        }
        return true;
    }

}
