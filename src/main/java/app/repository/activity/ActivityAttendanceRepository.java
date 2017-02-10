package app.repository.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityAttendanceId;
import app.repository.BaseRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActivityAttendanceRepository extends BaseRepository<ActivityAttendance, ActivityAttendanceId>, Repository<ActivityAttendance, ActivityAttendanceId> {

    List<ActivityAttendance> findByIdActivityId(Long id);

    List<ActivityAttendance> findByIdStudentId(Long id);

}
