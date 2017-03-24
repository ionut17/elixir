package app.repository.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityAttendanceId;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.model.user.Lecturer;
import app.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActivityGradeRepository extends BaseRepository<ActivityGrade, ActivityGradeId>, PagingAndSortingRepository<ActivityGrade, ActivityGradeId> {

    Page<ActivityGrade> findByIdActivityId(Long id, Pageable pageable);

    Page<ActivityGrade> findByIdStudentId(Long id, Pageable pageable);

    Page<ActivityGrade> findByActivityIdAndStudentId(Long activityId, Long studentId, Pageable pageable);

    Page<ActivityGrade> findByActivityCourseLecturers (Lecturer lecturer, Pageable pageable);

    Page<ActivityGrade> findDistinctByActivityNameOrActivityCourseTitleOrStudentFirstNameOrStudentLastNameAllIgnoreCaseContaining(String activityName, String courseTitle, String studentFirstName, String studentLastName, Pageable pageable);

}
