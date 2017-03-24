package app.controller.activity;

import app.controller.common.BaseController;
import app.model.Course;
import app.model.Pager;
import app.model.activity.*;
import app.model.dto.ActivityPostDto;
import app.model.dto.CourseDto;
import app.service.CourseService;
import app.service.activity.ActivityAttendanceService;
import app.service.activity.ActivityFileService;
import app.service.activity.ActivityGradeService;
import app.service.activity.ActivityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ActivityController extends BaseController {

    @Autowired
    ActivityService activityService;
    @Autowired
    ActivityAttendanceService activityAttendanceService;
    @Autowired
    ActivityGradeService activityGradeService;
    @Autowired
    ActivityFileService activityFileService;

    //Other dependencies
    @Autowired
    CourseService courses;
    @Autowired
    private ModelMapper modelMapper;

    //-------------------Retrieve All Activities--------------------------------------------------------

    @RequestMapping(value = "/activities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody ResponseEntity listAllActivities(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<Activity> activities;
        if (query!=null){
            activities = activityService.searchByPage(query, targetPage);
        } else{
            activities = activityService.findAllByPage(targetPage);
        }
        if (activities==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (activities.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(activities);
        toReturn.put("content", activities.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/activities/details/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Activity> getActivityById(@PathVariable("id") Long id) {
        Activity activity = activityService.findById(id);
        if (activity==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @RequestMapping(value = "/activities/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Activity> getActivityTypes() {
        List<ActivityType> types = activityService.findAllTypes();
        if (types==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (types.size()==0){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(types, HttpStatus.OK);
    }

    //-------------------Create Activity--------------------------------------------------------

    @RequestMapping(value = "/activities", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<Void> createActivity(@Valid @RequestBody ActivityPostDto activityPostDto, UriComponentsBuilder ucBuilder) {
        CourseDto courseDto = courses.findById(activityPostDto.getCourseId());
        Course course = modelMapper.map(courseDto, Course.class);
        if (course==null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        ActivityType activityType = activityService.findType(activityPostDto.getTypeId());
        if (activityType==null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Activity activity = new Activity();
        activity.setCourse(course);
        activity.setType(activityType);
        activity.setName(activityPostDto.getName());
        activity.setDate(new Date(activityPostDto.getDate()));
        activity = activityService.add(activity);
        if (activity==null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/activities/{id}").buildAndExpand(activity.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //-------------------Retrieve All Activities View--------------------------------------------------------

    @RequestMapping(value = "/activities/join", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllActivitiesJoin(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityJoin> activities;
        if (query!=null){
            activities = activityService.searchJoinByPage(query, targetPage);
        } else{
            activities = activityService.findAllJoinByPage(targetPage);
        }
        if (activities==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (activities.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(activities);
        toReturn.put("content", activities.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/activities/join/{activity_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllActivitiesJoin(@PathVariable("activity_id") Long activityId, @RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityJoin> activities = activityService.findByActivityIdJoinByPage(activityId, targetPage);
        if (activities==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (activities.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(activities);
        toReturn.put("content", activities.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //-------------------Retrieve All Activities By Course--------------------------------------------------------

    @RequestMapping(value = "/activities/course/{course_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllActivitiesOfCourse(@PathVariable("course_id") long courseId, @RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<Activity> activities = activityService.findByCourseByPage(courseId, targetPage);
        if (activities==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (activities.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(activities);
        toReturn.put("content", activities.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //-------------------Retrieve All Attendances--------------------------------------------------------

    @RequestMapping(value = "/attendances", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllAttendances(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityAttendance> attendances;
        if (query!=null){
            attendances = activityAttendanceService.searchByPage(query, targetPage);
        } else{
            attendances = activityAttendanceService.findAllByPage(targetPage);
        }
        if (attendances==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (attendances.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(attendances);
        toReturn.put("content", attendances.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/attendances/{activity_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAttendancesOfActivity(@PathVariable("activity_id") long activityId) {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityAttendance> attendances = activityAttendanceService.findByActivityIdByPage(activityId, 0);
        if (attendances==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (attendances.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(attendances);
        toReturn.put("content", attendances.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/attendances/{activity_id}", params = {"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAttendancesOfActivity(@PathVariable("activity_id") long activityId, @RequestParam("page") int page) {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityAttendance> attendances = activityAttendanceService.findByActivityIdByPage(activityId, page);
        if (attendances==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (attendances.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(attendances);
        toReturn.put("content", attendances.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/attendances/{activity_id}/{student_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityAttendance> getAttendanceByCompositeId(@PathVariable("activity_id") long activityId, @PathVariable("student_id") long studentId) {
        ActivityAttendance attendance = activityAttendanceService.findById(new ActivityAttendanceId(activityId, studentId));
        if (attendance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }

    //-------------------Retrieve All Grades--------------------------------------------------------

    @RequestMapping(value = "/grades", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllGrades(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityGrade> grades;
        if (query!=null){
            grades = activityGradeService.searchByPage(query, targetPage);
        } else{
            grades = activityGradeService.findAllByPage(targetPage);
        }
        if (grades==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Pager pager = new Pager(grades);
        toReturn.put("content", grades.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/grades/{activity_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGradesOfActivity(@PathVariable("activity_id") long activityId) {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityGrade> grades = activityGradeService.findByActivityIdByPage(activityId, 0);
        if (grades==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (grades.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(grades);
        toReturn.put("content", grades.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/grades/{activity_id}", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGradesOfActivity(@PathVariable("activity_id") long activityId, @RequestParam("page") int page) {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityGrade> grades = activityGradeService.findByActivityIdByPage(activityId, page);
        if (grades==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (grades.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(grades);
        toReturn.put("content", grades.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/grades/{activity_id}/{student_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityGrade> getGradesByCompositeId(@PathVariable("activity_id") long activityId, @PathVariable("student_id") long studentId) {
        ActivityGrade attendance = activityGradeService.findById(new ActivityGradeId(activityId, studentId));
        if (attendance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }

    //-------------------Retrieve All Files--------------------------------------------------------

    @RequestMapping(value = "/files", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllFiles(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityFile> files;
        if (query!=null){
            files = activityFileService.searchByPage(query, targetPage);
        } else{
            files = activityFileService.findAllByPage(targetPage);
        }
        if (files==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Pager pager = new Pager(files);
        toReturn.put("content", files.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/files/{activity_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFilesOfActivity(@PathVariable("activity_id") long activityId, @RequestParam(value="page", required = false) Integer page) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityFile> files = activityFileService.findByActivityIdByPage(activityId, targetPage);
        if (files==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (files.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(files);
        toReturn.put("content", files.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/files/{activity_id}/{student_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFilesByCompositeId(@PathVariable("activity_id") long activityId, @PathVariable("student_id") long studentId, @RequestParam(value="page", required = false) Integer page) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityFile> files = activityFileService.findByActivityIdAndStudentIdByPage(activityId, studentId, targetPage); //new ActivityFileId(activityId, studentId)
        if (files==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (files.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(files);
        toReturn.put("content", files.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(files, HttpStatus.OK);
    }

    @RequestMapping(value = "/files/file/{file_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityFile> getFilesById(@PathVariable("file_id") Long fileId) {
        ActivityFile file = activityFileService.findById(fileId);
        if (file == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

}
