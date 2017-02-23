package app.service.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.service.common.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActivityGradeService extends BaseService<ActivityGrade, ActivityGradeId> {

    Page<ActivityGrade> findByActivityIdByPage(long activityId, int page);
}
