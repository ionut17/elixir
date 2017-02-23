package app.service.activity;

import app.model.activity.Activity;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.model.user.Lecturer;
import app.model.user.User;
import app.repository.LecturerRepository;
import app.repository.activity.ActivityAttendanceRepository;
import app.repository.activity.ActivityGradeRepository;
import app.repository.activity.ActivityRepository;
import app.service.AuthDetailsService;
import app.service.JwtService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Component("activityGradeService")
public class ActivityGradeServiceImpl implements ActivityGradeService {

    @Autowired
    AuthDetailsService authDetailsService;
    @Autowired
    ActivityGradeRepository activityGrades;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    private LecturerRepository lecturers;

    public ActivityGradeServiceImpl() {

    }

    @Override
    public List<ActivityGrade> findAll() {
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activityGrades.findByIdStudentId(authenticatedUser.getId(), new PageRequest(0, 10)).getContent();
            case "lecturer":
            case "admin":
                return activityGrades.findAll();
        }
        return activityGrades.findAll();
    }

    @Override
    public Page<ActivityGrade> findAllByPage(int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        Type listType = new TypeToken<Page<ActivityGrade>>() {}.getType();
        switch (authenticatedUser.getType()) {
            case "student":
                return activityGrades.findByIdStudentId(authenticatedUser.getId(), new PageRequest(page, 10));
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                return activityGrades.findByActivityCourseLecturers(lecturer, new PageRequest(page, 10));
            case "admin":
                return activityGrades.findAll(new PageRequest(page, 10));
        }
        return activityGrades.findAll(new PageRequest(page, 10));
    }

    @Override
    public ActivityGrade findById(ActivityGradeId id) {
        return activityGrades.findOne(id);
    }

    @Override
    public ActivityGrade add(ActivityGrade entity) {
        return activityGrades.save(entity);
    }

    @Override
    public ActivityGrade update(ActivityGrade user) {
        return null;
    }

    @Override
    public void remove(ActivityGradeId id) {
        activityGrades.delete(id);
    }

    @Override
    public boolean entityExist(ActivityGrade entity) {
        return false;
    }

    @Override
    public Page<ActivityGrade> findByActivityIdByPage(long id, int page) {
        Activity activity = activityRepository.findOne(id);
        if (activity == null) {
            return null;
        }
        return activityGrades.findByIdActivityId(id, new PageRequest(page, 10));
    }
}
