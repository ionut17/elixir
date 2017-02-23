package app.service.activity;

import app.model.activity.Activity;
import app.model.activity.ActivityJoin;
import app.model.user.Lecturer;
import app.model.user.Student;
import app.model.user.User;
import app.repository.LecturerRepository;
import app.repository.StudentRepository;
import app.repository.activity.ActivityJoinRepository;
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

@Component("activityService")
public class ActivityServiceImpl implements ActivityService {

    //Main repository
    @Autowired
    ActivityJoinRepository activitiesJoin;

    //Other repositories
    @Autowired
    ActivityRepository activities;
    @Autowired
    private LecturerRepository lecturers;

    //Other dependencies
    @Autowired
    AuthDetailsService authDetailsService;

    public ActivityServiceImpl() {

    }

    @Override
    public List<Activity> findAll() {
        return activities.findAll();
    }

    @Override
    public Page<Activity> findAllByPage(int page) {
        return activities.findAll(new PageRequest(page, 10));
    }

    public List<ActivityJoin> findAllJoin() {
        //Return activities of student or all for lecturer/admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activitiesJoin.findByIdUserIdId(authenticatedUser.getId(), new PageRequest(0, 10)).getContent();
            case "lecturer":
                //TODO return only lecturer's courses activities
            case "admin":
                return activitiesJoin.findAll();
        }
        return activitiesJoin.findAll();
    }

    @Override
    public Page<ActivityJoin> findAllJoinByPage(int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        Type listType = new TypeToken<Page<ActivityJoin>>() {}.getType();
        switch (authenticatedUser.getType()) {
            case "student":
                return activitiesJoin.findByIdUserIdId(authenticatedUser.getId(), new PageRequest(page, 10));
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                return activitiesJoin.findByActivityCourseLecturers(lecturer, new PageRequest(page, 10));
            case "admin":
                return activitiesJoin.findAll(new PageRequest(page, 10));
        }
        return activitiesJoin.findAll(new PageRequest(page, 10));
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
