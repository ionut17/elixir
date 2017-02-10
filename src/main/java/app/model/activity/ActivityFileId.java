package app.model.activity;

import java.io.Serializable;

/**
 * Created by Ionut on 04-Feb-17.
 */
public class ActivityFileId implements Serializable{

    private Long studentId;
    private Long activityId;

    public ActivityFileId() {};

    public ActivityFileId(Long activityId, Long studentId){
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
