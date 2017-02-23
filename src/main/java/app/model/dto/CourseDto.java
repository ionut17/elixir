package app.model.dto;

import app.model.Course;
import app.model.Group;
import app.model.activity.Activity;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityFile;
import app.model.activity.ActivityGrade;
import app.model.common.Item;
import app.model.user.Lecturer;
import app.model.user.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class CourseDto implements Item {

    private Long id;

    private String title;

    private int year;

    private int semester;

    private List<Student> students;

    private List<Lecturer> lecturers;

    private List<Activity> activities;

    //Constructors

    public CourseDto() {
    }

    //Setters and getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

}
