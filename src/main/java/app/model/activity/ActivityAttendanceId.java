package app.model.activity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ActivityAttendanceId implements Serializable {

    private Long activityId;
    private Long studentId;

    public ActivityAttendanceId() {};

    public ActivityAttendanceId(Long activityId, Long studentId){
        this.setActivityId(activityId);
        this.setStudentId(studentId);
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
