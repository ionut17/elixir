package app.service.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.model.dto.StudentGradeDto;
import app.service.common.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ActivityGradeService extends BaseService<ActivityGrade, ActivityGradeId> {

    Page<ActivityGrade> findByActivityIdByPage(long activityId, int page);

    List<ActivityGrade> findByActivityCourseId(long id);

    List<ActivityGrade> addMultipleGrades(List<StudentGradeDto> studentsGrades, Long activityId);

}
