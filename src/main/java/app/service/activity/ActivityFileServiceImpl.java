package app.service.activity;

import app.model.activity.*;
import app.model.user.Lecturer;
import app.model.user.User;
import app.repository.LecturerRepository;
import app.repository.activity.ActivityFileRepository;
import app.repository.activity.ActivityGradeRepository;
import app.repository.activity.ActivityRepository;
import app.service.AuthDetailsService;
import app.service.JwtService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Component("activityFileService")
public class ActivityFileServiceImpl implements ActivityFileService {

    @Autowired
    AuthDetailsService authDetailsService;
    @Autowired
    ActivityFileRepository activityFiles;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    private LecturerRepository lecturers;

    public ActivityFileServiceImpl() {

    }

    @Override
    public List<ActivityFile> findAll() {
        //Return courses of student/lecturer or all courses for admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activityFiles.findByIdStudentId(authenticatedUser.getId(), new PageRequest(0,10)).getContent();
            case "lecturer":
            case "admin":
                return activityFiles.findAll();
        }
        return activityFiles.findAll();
    }

    @Override
    public Page<ActivityFile> findAllByPage(int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        Type listType = new TypeToken<Page<ActivityFile>>() {}.getType();
        switch (authenticatedUser.getType()) {
            case "student":
                return activityFiles.findByIdStudentId(authenticatedUser.getId(), new PageRequest(page, 10));
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                return activityFiles.findByActivityCourseLecturers(lecturer, new PageRequest(page, 10));
            case "admin":
                return activityFiles.findAll(new PageRequest(page, 10));
        }
        return activityFiles.findAll(new PageRequest(page, 10));
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
    public Page<ActivityFile> findByActivityIdByPage(long id, int page) {
        Activity activity = activityRepository.findOne(id);
        if (activity == null){
            return null;
        }
        return activityFiles.findByIdActivityId(id, new PageRequest(page, 10));
    }

    @Override
    public ActivityFile findByFileId(long fileId) {
        return activityFiles.findByFileId(fileId);
    }
}
