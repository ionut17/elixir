package app.controller.activity;

import app.controller.common.BaseController;
import app.model.activity.Activity;
import app.model.activity.ActivityJoin;
import app.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActivityController extends BaseController {

    @Autowired
    ActivityService activityService;

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

}
