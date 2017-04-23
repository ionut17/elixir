package app.repository.activity;

import app.model.Course;
import app.model.activity.ActivityJoin;
import app.model.user.Lecturer;
import app.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActivityJoinRepository extends BaseRepository<ActivityJoin, Long>, PagingAndSortingRepository<ActivityJoin, Long> {

    Page<ActivityJoin> findByActivityId(Long id, Pageable pageable);

    Page<ActivityJoin> findByIdUserIdId(Long id, Pageable pageable);

    Page<ActivityJoin> findByIdActivityIdAndIdUserIdId(Long activityId, Long studentId, Pageable pageable);

    Page<ActivityJoin> findByActivityCourseId(Long id, Pageable pageable);

    Page<ActivityJoin> findByActivityCourseLecturers (Lecturer lecturer, Pageable pageable);

    Page<ActivityJoin> findByActivityCourse(List<Course> courses, Pageable pageable);

//    Page<ActivityJoin> findByActivityCourses(List<Course> courses, Pageable pageable);

    Page<ActivityJoin> findDistinctByActivityNameOrActivityCourseTitleOrUserFirstNameOrUserLastNameOrActivityTypeNameAllIgnoreCaseContaining(String activityName, String courseTitle, String studentFirstName, String studentLastName, String typeName, Pageable pageable);

}
