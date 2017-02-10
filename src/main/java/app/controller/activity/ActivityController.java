package app.controller.activity;

import app.controller.common.BaseController;
import app.model.activity.*;
import app.service.activity.ActivityAttendanceService;
import app.service.activity.ActivityFileService;
import app.service.activity.ActivityGradeService;
import app.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        if (activities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    //-------------------Retrieve All Activities View--------------------------------------------------------

    @RequestMapping(value = "/activities/join", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<List<ActivityJoin>> listAllActivitiesJoin() {
        List<ActivityJoin> activities = activityService.findAllJoin();
        if (activities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    //-------------------Retrieve All Attendances--------------------------------------------------------

    @RequestMapping(value = "/attendances", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<List<ActivityAttendance>> listAllAttendances() {
        List<ActivityAttendance> activities = activityAttendanceService.findAll();
        if (activities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @RequestMapping(value = "/attendances/{activity_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ActivityAttendance>> getAttendancesOfActivity(@PathVariable("activity_id") long activityId) {
        List<ActivityAttendance> attendances = activityAttendanceService.findByActivityId(activityId);
        if (attendances == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attendances, HttpStatus.OK);
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
    public @ResponseBody ResponseEntity<List<ActivityGrade>> listAllGrades() {
        List<ActivityGrade> grades = activityGradeService.findAll();
        if (grades.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @RequestMapping(value = "/grades/{activity_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ActivityGrade>> getGradesOfActivity(@PathVariable("activity_id") long activityId) {
        List<ActivityGrade> grades = activityGradeService.findByActivityId(activityId);
        if (grades == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(grades, HttpStatus.OK);
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
    public @ResponseBody ResponseEntity<List<ActivityFile>> listAllFiles() {
        List<ActivityFile> files = activityFileService.findAll();
        if (files.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @RequestMapping(value = "/files/{activity_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ActivityFile>> getFilesOfActivity(@PathVariable("activity_id") long activityId) {
        List<ActivityFile> files = activityFileService.findByActivityId(activityId);
        if (files == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(files, HttpStatus.OK);
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
