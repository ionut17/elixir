package app.service.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityGrade;
import app.repository.activity.ActivityAttendanceRepository;
import app.repository.activity.ActivityGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("activityGradeService")
public class ActivityGradeServiceImpl implements ActivityGradeService {

    @Autowired
    ActivityGradeRepository activityGrades;

    public ActivityGradeServiceImpl() {

    }

    @Override
    public List<ActivityGrade> findAll() {
        return activityGrades.findAll();
    }

    @Override
    public ActivityGrade findById(long id) {
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
    public void remove(Long id) {
        activityGrades.delete(id);
    }

    @Override
    public boolean entityExist(ActivityGrade entity) {
        return false;
    }

}
