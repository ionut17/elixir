package app.service.activity;

import app.model.activity.Activity;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityAttendanceId;
import app.model.user.Lecturer;
import app.model.user.Student;
import app.model.user.User;
import app.repository.LecturerRepository;
import app.repository.StudentRepository;
import app.repository.activity.ActivityAttendanceRepository;
import app.repository.activity.ActivityRepository;
import app.service.AuthDetailsService;
import app.service.JwtService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("activityAttendanceService")
public class ActivityAttendanceServiceImpl implements ActivityAttendanceService {

    @Autowired
    AuthDetailsService authDetailsService;
    @Autowired
    ActivityAttendanceRepository activityAttendances;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LecturerRepository lecturers;
    @Autowired
    private ModelMapper modelMapper;

    public ActivityAttendanceServiceImpl() {

    }

    @Override
    public List<ActivityAttendance> findAll() {
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activityAttendances.findByIdStudentId(authenticatedUser.getId(), new PageRequest(0, 10)).getContent();
            case "lecturer":
            case "admin":
                return activityAttendances.findAll();
        }
        return activityAttendances.findAll();
    }

    @Override
    public Page<ActivityAttendance> findAllByPage(int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        Type listType = new TypeToken<Page<ActivityAttendance>>() {}.getType();
        switch (authenticatedUser.getType()) {
            case "student":
                return activityAttendances.findByIdStudentId(authenticatedUser.getId(), new PageRequest(page, 10));
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                return activityAttendances.findByActivityCourseLecturers(lecturer, new PageRequest(page, 10));
            case "admin":
                return activityAttendances.findAll(new PageRequest(page, 10));
        }
        return activityAttendances.findAll(new PageRequest(page, 10));
    }

    @Override
    public Page<ActivityAttendance> searchByPage(String query, int page) {
        return activityAttendances.findDistinctByActivityNameOrActivityCourseTitleOrStudentFirstNameOrStudentLastNameAllIgnoreCaseContaining(query, query, query, query, new PageRequest(page, 10));
    }

    @Override
    public ActivityAttendance findById(ActivityAttendanceId id) {
        return activityAttendances.findOne(id);
    }

    @Override
    public ActivityAttendance add(ActivityAttendance entity) {
        return activityAttendances.save(entity);
    }

    @Override
    public List<ActivityAttendance> addMultipleAttendances(List<Long> studentIds, Long activityId) {
        List<ActivityAttendance> saved = new ArrayList<>();
        Activity currentActivity = activityRepository.findOne(activityId);
        if (currentActivity == null){
            return null;
        }
        for (Long studentId : studentIds){
            Student currentStudent = studentRepository.findOne(studentId);
            ActivityAttendance currentAttendance = new ActivityAttendance();
            currentAttendance.setId(new ActivityAttendanceId(currentActivity.getId(), currentStudent.getId()));
            currentAttendance.setActivity(currentActivity);
            currentAttendance.setStudent(modelMapper.map(currentStudent, Student.class));
            saved.add(activityAttendances.save(currentAttendance));
        }
        return saved;
    }

    @Override
    public ActivityAttendance update(ActivityAttendance user) {
        return null;
    }

    @Override
    public void remove(ActivityAttendanceId id) {
        activityAttendances.delete(id);
    }

    @Override
    public boolean entityExist(ActivityAttendance entity) {
        return false;
    }

    @Override
    public Page<ActivityAttendance> findByStudentIdByPage(long id, int page) {
        Student student = studentRepository.findOne(id);
        if (student == null){
            return null;
        }
        return activityAttendances.findByIdStudentId(id, new PageRequest(page, 10));
    }

    @Override
    public Page<ActivityAttendance> findByActivityIdByPage(long id, int page) {
        Activity activity = activityRepository.findOne(id);
        if (activity == null) {
            return null;
        }
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activityAttendances.findByActivityIdAndStudentId(id, authenticatedUser.getId(), new PageRequest(0, 10));
            case "lecturer":
            case "admin":
                return activityAttendances.findByIdActivityId(id, new PageRequest(page, 10));
        }
        return null;
    }
}
