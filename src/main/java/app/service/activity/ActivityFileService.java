package app.service.activity;

import app.model.activity.ActivityFile;
import app.model.activity.ActivityFileId;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.service.common.BaseService;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ActivityFileService extends BaseService<ActivityFile, ActivityFileId> {

    Page<ActivityFile> findByActivityIdByPage(long activityId, int page);

    ActivityFile findByFileId(long fileId);
}
