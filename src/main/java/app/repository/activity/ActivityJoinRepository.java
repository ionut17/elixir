package app.repository.activity;

import app.model.activity.ActivityJoin;
import app.repository.BaseRepository;
import org.springframework.data.repository.Repository;

public interface ActivityJoinRepository extends BaseRepository<ActivityJoin>, Repository<ActivityJoin, Long> {

}
