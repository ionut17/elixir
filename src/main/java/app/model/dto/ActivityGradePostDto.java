package app.model.dto;

import app.model.common.Item;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ActivityGradePostDto implements Item, Serializable{

    @NotNull
    @JsonProperty("studentsGrades")
    private List<StudentGradeDto> studentsGrades;

    @NotNull
    @JsonProperty("activityId")
    private Long activityId;

    //Constructors

    public ActivityGradePostDto() {
    }

    //Setters and getters

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public List<StudentGradeDto> getStudentsGrades() {
        return studentsGrades;
    }

    public void setStudentsGrades(List<StudentGradeDto> studentsGrades) {
        this.studentsGrades = studentsGrades;
    }
}
