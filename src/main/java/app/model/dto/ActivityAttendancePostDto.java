package app.model.dto;

import app.model.common.Item;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ActivityAttendancePostDto implements Item {

    @NotNull
    private List<Long> studentIds;

    @NotNull
    private Long activityId;

    //Constructors

    public ActivityAttendancePostDto() {
    }

    //Setters and getters

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
