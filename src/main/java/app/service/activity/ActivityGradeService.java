package app.service.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.service.common.BaseService;

import java.util.List;

public interface ActivityGradeService extends BaseService<ActivityGrade, ActivityGradeId> {

    List<ActivityGrade> findByActivityId(long activityId);
}
