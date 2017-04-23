package app.model.activity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof ActivityAttendanceId)) return false;
//        ActivityAttendanceId that = (ActivityAttendanceId) o;
//        return Objects.equals(getActivityId(), that.getActivityId()) &&
//                Objects.equals(getStudentId(), that.getStudentId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getActivityId(), getStudentId());
//    }
}
