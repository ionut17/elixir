package app.service.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityAttendanceId;
import app.service.common.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActivityAttendanceService extends BaseService<ActivityAttendance, ActivityAttendanceId> {

    Page<ActivityAttendance> findByActivityIdByPage(long id, int page);

    Page<ActivityAttendance> findByStudentIdByPage(long id, int page);
}
