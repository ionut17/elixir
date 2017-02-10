package app.repository.activity;

import app.model.activity.ActivityJoin;
import app.repository.BaseRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ActivityJoinRepository extends BaseRepository<ActivityJoin, Long>, Repository<ActivityJoin, Long> {

    List<ActivityJoin> findByIdUserIdId(Long id);

}
