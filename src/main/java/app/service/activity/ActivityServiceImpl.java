package app.service.activity;

import app.model.Course;
import app.model.activity.Activity;
import app.model.activity.ActivityJoin;
import app.model.activity.ActivityType;
import app.model.user.Lecturer;
import app.model.user.Student;
import app.model.user.User;
import app.repository.CourseRepository;
import app.repository.LecturerRepository;
import app.repository.StudentRepository;
import app.repository.activity.ActivityJoinRepository;
import app.repository.activity.ActivityRepository;
import app.repository.activity.ActivityTypeRepository;
import app.service.AuthDetailsService;
import app.service.JwtService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Component("activityService")
public class ActivityServiceImpl implements ActivityService {

    //Main repository
    @Autowired
    private ActivityJoinRepository activitiesJoin;

    //Other repositories
    @Autowired
    private ActivityRepository activities;
    @Autowired
    private StudentRepository students;
    @Autowired
    private LecturerRepository lecturers;
    @Autowired
    private CourseRepository courses;
    @Autowired
    private ActivityTypeRepository activityTypes;

    //Other dependencies
    @Autowired
    private AuthDetailsService authDetailsService;
    @Autowired
    private ModelMapper modelMapper;

    public ActivityServiceImpl() {

    }

    @Override
    public List<Activity> findAll() {
        return activities.findAll();
    }

    @Override
    public Page<Activity> findAllByPage(int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                Student student = students.findOne(authenticatedUser.getId());
                List<Course> courses = student.getCourses();
                return activities.findByCourseIn(courses, new PageRequest(page, 10, Sort.Direction.DESC, "date"));
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                return activities.findByCourseIn(lecturer.getCourses(), new PageRequest(page, 10, Sort.Direction.DESC, "date"));
            case "admin":
                return activities.findAll(new PageRequest(page, 10, Sort.Direction.DESC, "date"));
        }
        return null;
    }

    @Override
    public Page<Activity> searchByPage(String query, int page) {
        return activities.findDistinctByNameOrCourseTitleOrTypeNameAllIgnoreCaseContaining(query, query, query, new PageRequest(page, 10, Sort.Direction.DESC, "date"));
    }

    public List<ActivityJoin> findAllJoin() {
        //Return activities of student or all for lecturer/admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activitiesJoin.findByIdUserIdId(authenticatedUser.getId(), new PageRequest(0, 10)).getContent();
            case "lecturer":
                //TODO return only lecturer's courses activities
            case "admin":
                return activitiesJoin.findAll();
        }
        return activitiesJoin.findAll();
    }

    @Override
    public List<ActivityType> findAllTypes() {
        return activityTypes.findAll();
    }

    @Override
    public ActivityType findType(Long id) { return activityTypes.findOne(id); }

    @Override
    public Page<ActivityJoin> findByActivityIdJoinByPage(Long id, int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return activitiesJoin.findByIdActivityIdAndIdUserIdId(id, authenticatedUser.getId(), new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
            case "lecturer":
            case "admin":
                return activitiesJoin.findByActivityId(id, new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
        }
        return null;
    }

    @Override
    public Page<Activity> findByCourseByPage(long id, int page) {
        //Return activities of student or all for lecturer/admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                Student student = students.findOne(authenticatedUser.getId());
                Course course1 = courses.findOne(id);
                if (student.getCourses().contains(course1)){
                    return activities.findByCourseId(id, new PageRequest(page, 10, Sort.Direction.DESC, "date"));
                } else{
                    return null;
                }
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                Course course2 = courses.findOne(id);
                if (lecturer.getCourses().contains(course2)){
                    return activities.findByCourseId(id, new PageRequest(page, 10, Sort.Direction.DESC, "date"));
                } else{
                    return null;
                }
            case "admin":
                return activities.findByCourseId(id, new PageRequest(page, 10, Sort.Direction.DESC, "date"));
        }
        return activities.findByCourseId(id, new PageRequest(page, 10, Sort.Direction.DESC, "date"));
    }

    @Override
    public Page<ActivityJoin> findByCourseJoinByPage(long id, int page) {
        //Return activities of student or all for lecturer/admin
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                Student student = students.findOne(authenticatedUser.getId());
                Course course1 = courses.findOne(id);
                if (student.getCourses().contains(course1)){
                    return activitiesJoin.findByActivityCourseId(id, new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
                } else{
                    return null;
                }
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                Course course2 = courses.findOne(id);
                if (lecturer.getCourses().contains(course2)){
                    return activitiesJoin.findByActivityCourseId(id, new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
                } else{
                    return null;
                }
            case "admin":
                return activitiesJoin.findByActivityCourseId(id, new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
        }
        return activitiesJoin.findByActivityCourseId(id, new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
    }

    @Override
    public Page<ActivityJoin> findAllJoinByPage(int page) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        Type listType = new TypeToken<Page<ActivityJoin>>() {}.getType();
        switch (authenticatedUser.getType()) {
            case "student":
                return activitiesJoin.findByIdUserIdId(authenticatedUser.getId(), new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                return activitiesJoin.findByActivityCourseLecturers(lecturer, new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
            case "admin":
                return activitiesJoin.findAll(new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
        }
        return activitiesJoin.findAll(new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
    }

    public Page<ActivityJoin> searchJoinByPage(String query, int page){
        return activitiesJoin.findDistinctByActivityNameOrActivityCourseTitleOrUserFirstNameOrUserLastNameOrActivityTypeNameAllIgnoreCaseContaining(query, query, query, query, query, new PageRequest(page, 10, Sort.Direction.DESC, "activity.date"));
    }

    @Override
    public Activity findById(Long id) {
        return activities.findOne(id);
    }

    @Override
    public Activity add(Activity entity) {
        User authenticatedUser = authDetailsService.getAuthenticatedUser();
        switch (authenticatedUser.getType()) {
            case "student":
                return null;
            case "lecturer":
                Lecturer lecturer = lecturers.findOne(authenticatedUser.getId());
                Course course = courses.findOne(entity.getCourse().getId());
                if (lecturer.getCourses().contains(course)){
                    return activities.save(entity);
                }
                return null;
            case "admin":
                return activities.save(entity);
        }
        return null;
    }

    @Override
    public Activity update(Activity user) {
        return null;
    }

    @Override
    public void remove(Long id) {

        activities.delete(id);
    }

    @Override
    public boolean entityExist(Activity entity) {
        Activity found = activities.findOne(entity.getId());
        if (found == null) {
            return false;
        }
        return true;
    }

}
