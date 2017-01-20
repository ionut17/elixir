package app.service.activity;

import app.model.activity.ActivityAttendance;
import app.repository.activity.ActivityAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("activityAttendanceService")
public class ActivityAttendanceServiceImpl implements ActivityAttendanceService {

    @Autowired
    ActivityAttendanceRepository activityAttendances;

    public ActivityAttendanceServiceImpl() {

    }

    @Override
    public List<ActivityAttendance> findAll() {
        return activityAttendances.findAll();
    }

    @Override
    public ActivityAttendance findById(long id) {
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
    public void remove(Long id) {
        activityAttendances.delete(id);
    }

    @Override
    public boolean entityExist(ActivityAttendance entity) {
        return false;
    }

}
