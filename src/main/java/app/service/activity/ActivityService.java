package app.service.activity;

import app.model.activity.Activity;
import app.model.activity.ActivityJoin;
import app.service.common.BaseService;

import java.util.List;

public interface ActivityService extends BaseService<Activity> {

    List<ActivityJoin> findAllJoin();

}
