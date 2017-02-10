package app.service.activity;

import app.model.activity.Activity;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityAttendanceId;
import app.model.user.User;
import app.repository.activity.ActivityAttendanceRepository;
import app.repository.activity.ActivityRepository;
import app.service.AuthDetailsService;
import app.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("activityAttendanceService")
public class ActivityAttendanceServiceImpl implements ActivityAttendanceService {

    @Autowired
    AuthDetailsService authDetailsService;
    @Autowired
    ActivityAttendanceRepository activityAttendances;
    @Autowired
    ActivityRepository activityRepository;

    public ActivityAttendanceServiceImpl() {

    }

    @Override
    public List<ActivityAttendance> findAll() {
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activityAttendances.findByIdStudentId(authenticatedUser.getId());
            case "lecturer":
            case "admin":
                return activityAttendances.findAll();
        }
        return activityAttendances.findAll();
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
    public List<ActivityAttendance> findByActivityId(Long id) {
        Activity activity = activityRepository.findOne(id);
        if (activity == null){
            return null;
        }
        return activityAttendances.findByIdActivityId(id);
    }
}
