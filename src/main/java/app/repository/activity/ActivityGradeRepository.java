package app.repository.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityGrade;
import app.repository.BaseRepository;
import org.springframework.data.repository.Repository;

public interface ActivityGradeRepository extends BaseRepository<ActivityGrade>, Repository<ActivityGrade, Long> {

}
