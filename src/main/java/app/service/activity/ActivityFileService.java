package app.service.activity;

import app.model.activity.ActivityFile;
import app.model.activity.ActivityFileId;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.service.common.BaseService;

import java.util.List;

public interface ActivityFileService extends BaseService<ActivityFile, ActivityFileId> {

    List<ActivityFile> findByActivityId(long activityId);

    ActivityFile findByFileId(long fileId);
}
