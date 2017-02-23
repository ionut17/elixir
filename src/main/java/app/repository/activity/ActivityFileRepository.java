package app.repository.activity;

import app.model.activity.ActivityFile;
import app.model.activity.ActivityFileId;
import app.model.activity.ActivityGrade;
import app.model.activity.ActivityGradeId;
import app.model.user.Lecturer;
import app.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActivityFileRepository extends BaseRepository<ActivityFile, ActivityFileId>, PagingAndSortingRepository<ActivityFile, ActivityFileId> {

    Page<ActivityFile> findByIdActivityId(Long id, Pageable pageable);

    Page<ActivityFile> findByIdStudentId(Long id, Pageable pageable);

    Page<ActivityFile> findByActivityCourseLecturers (Lecturer lecturer, Pageable pageable);

    ActivityFile findByFileId(Long id);

}
