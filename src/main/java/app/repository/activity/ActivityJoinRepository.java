package app.repository.activity;

import app.model.activity.ActivityJoin;
import app.model.user.Lecturer;
import app.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActivityJoinRepository extends BaseRepository<ActivityJoin, Long>, PagingAndSortingRepository<ActivityJoin, Long> {

    Page<ActivityJoin> findByIdUserIdId(Long id, Pageable pageable);

    Page<ActivityJoin> findByActivityCourseLecturers (Lecturer lecturer, Pageable pageable);

}
