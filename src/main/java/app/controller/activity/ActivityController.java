package app.controller.activity;

import app.controller.common.BaseController;
import app.model.Pager;
import app.model.activity.*;
import app.service.activity.ActivityAttendanceService;
import app.service.activity.ActivityFileService;
import app.service.activity.ActivityGradeService;
import app.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //-------------------Retrieve All Activities--------------------------------------------------------

    @RequestMapping(value = "/activities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<List<Activity>> listAllActivities() {
        List<Activity> activities = activityService.findAll();
        if (activities==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (activities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    //-------------------Retrieve All Activities View--------------------------------------------------------

    @RequestMapping(value = "/activities/join", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllActivitiesJoin() {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityJoin> activities = activityService.findAllJoinByPage(0);
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

    @RequestMapping(value = "/activities/join", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllActivitiesJoin(@RequestParam("page") int page) {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityJoin> activities = activityService.findAllJoinByPage(page);
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
    public @ResponseBody ResponseEntity listAllAttendances() {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityAttendance> attendances = activityAttendanceService.findAllByPage(0);
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

    @RequestMapping(value = "/attendances", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllAttendances(@RequestParam("page") int page) {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityAttendance> attendances = activityAttendanceService.findAllByPage(page);
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
    public @ResponseBody ResponseEntity listAllGrades() {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityGrade> grades = activityGradeService.findAllByPage(0);
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

    @RequestMapping(value = "/grades", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllGrades(@RequestParam("page") int page) {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityGrade> grades = activityGradeService.findAllByPage(page);
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
    public @ResponseBody ResponseEntity listAllFiles() {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityFile> files = activityFileService.findAllByPage(0);
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

    @RequestMapping(value = "/files", params = {"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllFiles(@RequestParam("page") int page) {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityFile> files = activityFileService.findAllByPage(page);
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

    @RequestMapping(value = "/files/{activity_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFilesOfActivity(@PathVariable("activity_id") long activityId) {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityFile> files = activityFileService.findByActivityIdByPage(activityId, 0);
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

    @RequestMapping(value = "/files/{activity_id}", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFilesOfActivity(@PathVariable("activity_id") long activityId, @RequestParam("page") int page) {
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityFile> files = activityFileService.findByActivityIdByPage(activityId, page);
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
    public ResponseEntity<ActivityFile> getFilesByCompositeId(@PathVariable("activity_id") long activityId, @PathVariable("student_id") long studentId) {
        ActivityFile attendance = activityFileService.findById(new ActivityFileId(activityId, studentId));
        if (attendance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attendance, HttpStatus.OK);
    }

}
