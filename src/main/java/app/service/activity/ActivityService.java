package app.service.activity;

import app.model.activity.Activity;
import app.model.activity.ActivityJoin;
import app.model.activity.ActivityType;
import app.service.common.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActivityService extends BaseService<Activity, Long> {

    List<ActivityJoin> findAllJoin();

    List<ActivityType> findAllTypes();

    ActivityType findType(Long id);

    Page<ActivityJoin> findByActivityIdJoinByPage(Long id, int page);

    Page<Activity> findByCourseByPage(long id, int page);

    Page<ActivityJoin> findByCourseJoinByPage(long id, int page);

    Page<ActivityJoin> findAllJoinByPage(int page);

    Page<ActivityJoin> searchJoinByPage(String query, int page);

}
