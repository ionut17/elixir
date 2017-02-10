package app.service.activity;

import app.model.activity.*;
import app.model.user.User;
import app.repository.activity.ActivityFileRepository;
import app.repository.activity.ActivityGradeRepository;
import app.repository.activity.ActivityRepository;
import app.service.AuthDetailsService;
import app.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("activityFileService")
public class ActivityFileServiceImpl implements ActivityFileService {

    @Autowired
    AuthDetailsService authDetailsService;
    @Autowired
    ActivityFileRepository activityFiles;
    @Autowired
    ActivityRepository activityRepository;

    public ActivityFileServiceImpl() {

    }

    @Override
    public List<ActivityFile> findAll() {
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activityFiles.findByIdStudentId(authenticatedUser.getId());
            case "lecturer":
            case "admin":
                return activityFiles.findAll();
        }
        return activityFiles.findAll();
    }

    @Override
    public ActivityFile findById(ActivityFileId id) {
        return activityFiles.findOne(id);
    }

    @Override
    public ActivityFile add(ActivityFile entity) {
        return activityFiles.save(entity);
    }

    @Override
    public ActivityFile update(ActivityFile user) {
        return null;
    }

    @Override
    public void remove(ActivityFileId id) {
        activityFiles.delete(id);
    }

    @Override
    public boolean entityExist(ActivityFile entity) {
        return false;
    }

    @Override
    public List<ActivityFile> findByActivityId(long id) {
        Activity activity = activityRepository.findOne(id);
        if (activity == null){
            return null;
        }
        return activityFiles.findByIdActivityId(id);
    }

    @Override
    public ActivityFile findByFileId(long fileId) {
        return activityFiles.findByFileId(fileId);
    }
}
