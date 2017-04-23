package app.repository.activity;

import app.model.Course;
import app.model.activity.Activity;
import app.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActivityRepository extends BaseRepository<Activity, Long>, PagingAndSortingRepository<Activity, Long> {

    Page<Activity> findAll(Pageable pageable);

    Page<Activity> findByCourseId(Long id, Pageable pageable);

    Page<Activity> findByCourseIn(List<Course> courses, Pageable pageable);

    Page<Activity> findDistinctByNameOrCourseTitleOrTypeNameAllIgnoreCaseContaining(String activityName, String courseTitle, String typeName, Pageable pageable);

}
