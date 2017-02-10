package app.service.activity;

import app.model.activity.Activity;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.model.user.User;
import app.repository.activity.ActivityAttendanceRepository;
import app.repository.activity.ActivityGradeRepository;
import app.repository.activity.ActivityRepository;
import app.service.AuthDetailsService;
import app.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("activityGradeService")
public class ActivityGradeServiceImpl implements ActivityGradeService {

    @Autowired
    AuthDetailsService authDetailsService;
    @Autowired
    ActivityGradeRepository activityGrades;
    @Autowired
    ActivityRepository activityRepository;

    public ActivityGradeServiceImpl() {

    }

    @Override
    public List<ActivityGrade> findAll() {
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activityGrades.findByIdStudentId(authenticatedUser.getId());
            case "lecturer":
            case "admin":
                return activityGrades.findAll();
        }
        return activityGrades.findAll();
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
    public List<ActivityGrade> findByActivityId(long id) {
        Activity activity = activityRepository.findOne(id);
        if (activity == null) {
            return null;
        }
        return activityGrades.findByIdActivityId(id);
    }
}
