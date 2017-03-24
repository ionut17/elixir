package app.model.dto;

import app.model.activity.Activity;
import app.model.common.Item;
import app.model.user.Lecturer;
import app.model.user.Student;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ActivityPostDto implements Item {

    @NotNull
    private Long date;

    @NotNull
    private String name;

    @NotNull
    private Long courseId;

    @NotNull
    private Long typeId;

    //Constructors

    public ActivityPostDto() {
    }

    //Setters and getters

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
