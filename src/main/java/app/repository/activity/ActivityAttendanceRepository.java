package app.repository.activity;

import app.model.activity.ActivityAttendance;
import app.repository.BaseRepository;
import org.springframework.data.repository.Repository;

public interface ActivityAttendanceRepository extends BaseRepository<ActivityAttendance>, Repository<ActivityAttendance, Long> {

}
