package app.repository.activity;

import app.model.activity.Activity;
import app.repository.BaseRepository;
import org.springframework.data.repository.Repository;

public interface ActivityRepository extends BaseRepository<Activity>, Repository<Activity, Long> {

}
