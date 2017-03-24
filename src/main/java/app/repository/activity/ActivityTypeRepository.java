package app.repository.activity;

import app.model.activity.Activity;
import app.model.activity.ActivityType;
import app.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActivityTypeRepository extends BaseRepository<ActivityType, Long>, PagingAndSortingRepository<ActivityType, Long> {

    ActivityType findOne(Long id);

}
