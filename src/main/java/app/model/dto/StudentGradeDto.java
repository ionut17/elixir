package app.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Ionut on 26-Mar-17.
 */
public class StudentGradeDto implements Serializable {

    @JsonProperty("studentId")
    private Long studentId;

    @JsonProperty("grade")
    private Double grade;

    public StudentGradeDto(){}

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
