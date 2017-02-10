package app.repository.activity;

import app.model.activity.ActivityFile;
import app.model.activity.ActivityFileId;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.repository.BaseRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActivityFileRepository extends BaseRepository<ActivityFile, ActivityFileId>, Repository<ActivityFile, ActivityFileId> {

    List<ActivityFile> findByIdActivityId(Long id);

    List<ActivityFile> findByIdStudentId(Long id);

    ActivityFile findByFileId(Long id);

}
