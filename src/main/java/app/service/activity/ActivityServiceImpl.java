package app.service.activity;

import app.model.activity.Activity;
import app.model.activity.ActivityJoin;
import app.repository.activity.ActivityJoinRepository;
import app.repository.activity.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("activityService")
public class ActivityServiceImpl implements ActivityService {

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
