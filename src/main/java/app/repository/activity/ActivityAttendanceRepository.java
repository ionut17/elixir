package app.repository.activity;

import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityAttendanceId;
import app.model.user.Lecturer;
import app.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActivityAttendanceRepository extends BaseRepository<ActivityAttendance, ActivityAttendanceId>, PagingAndSortingRepository<ActivityAttendance, ActivityAttendanceId> {

    Page<ActivityAttendance> findByIdActivityId(Long id, Pageable pageable);

    Page<ActivityAttendance> findByIdStudentId(Long id, Pageable pageable);

    Page<ActivityAttendance> findByActivityCourseLecturers (Lecturer lecturer, Pageable pageable);

}
