package app.service.activity;

import app.model.activity.Activity;
import app.model.activity.ActivityJoin;
import app.model.user.Student;
import app.model.user.User;
import app.repository.LecturerRepository;
import app.repository.StudentRepository;
import app.repository.activity.ActivityJoinRepository;
import app.repository.activity.ActivityRepository;
import app.service.AuthDetailsService;
import app.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    AuthDetailsService authDetailsService;
    @Autowired
    ActivityRepository activities;
    @Autowired
    ActivityJoinRepository activitiesJoin;

    public ActivityServiceImpl() {

    }

    @Override
    public List<Activity> findAll() {
        return activities.findAll();
    }

    public List<ActivityJoin> findAllJoin() {
        //Return activities of student or all for lecturer/admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activitiesJoin.findByIdUserIdId(authenticatedUser.getId());
            case "lecturer":
                //TODO return only lecturer's courses activities
            case "admin":
                return activitiesJoin.findAll();
        }
        return activitiesJoin.findAll();
    }

    @Override
    public Activity findById(Long id) {
        return activities.findOne(id);
    }

    @Override
    public Activity add(Activity entity) {
        return activities.save(entity);
    }

    @Override
    public Activity update(Activity user) {
        return null;
    }

    @Override
    public void remove(Long id) {
        activities.delete(id);
    }

    @Override
    public boolean entityExist(Activity entity) {
        Activity found = activities.findOne(entity.getId());
        if (found == null) {
            return false;
        }
        return true;
    }

}
