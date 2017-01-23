package app.service.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityAttendanceId;
import app.service.common.BaseService;

import java.util.List;

public interface ActivityAttendanceService extends BaseService<ActivityAttendance, ActivityAttendanceId> {

    List<ActivityAttendance> findByActivityId(Long id);
}
