package app.repository.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityAttendanceId;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.repository.BaseRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActivityGradeRepository extends BaseRepository<ActivityGrade, ActivityGradeId>, Repository<ActivityGrade, ActivityGradeId> {

    List<ActivityGrade> findByIdActivityId(Long id);

    List<ActivityGrade> findByIdStudentId(Long id);

}
