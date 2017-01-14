package app.controller.activity;

import app.controller.common.BaseController;
import app.model.activity.ActivityAttendance;
import app.service.activity.ActivityAttendanceService;
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
public class ActivityAttendanceController extends BaseController {

    @Autowired
    ActivityAttendanceService activityAttendanceService;

    //-------------------Retrieve All Activities--------------------------------------------------------

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

}
