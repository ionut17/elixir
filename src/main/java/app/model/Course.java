package app.model;

import app.model.activity.Activity;
import app.model.common.Item;
import app.model.serializer.CustomActivityListSerializer;
import app.model.serializer.CustomLecturerListSerializer;
import app.model.serializer.CustomStudentListSerializer;
import app.model.user.Lecturer;
import app.model.user.Student;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "COURSES")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Course implements Item, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "year", nullable = false, length = 1)
    private int year;

    @Column(name = "semester", nullable = false, length = 1)
    private int semester;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, targetEntity = Lecturer.class, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonSerialize(using = CustomLecturerListSerializer.class)
    private List<Lecturer> lecturers;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, targetEntity = Student.class, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonSerialize(using = CustomStudentListSerializer.class)
    private List<Student> students;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, targetEntity = Activity.class, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
//    @JsonSerialize(using = CustomActivityListSerializer.class)
    private List<Activity> activities;

    public Course(String name, int yearOfStudy) {
        this.setTitle(name);
        this.setYear(yearOfStudy);
    }

    protected Course() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int yearOfStudy) {
        this.year = yearOfStudy;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public boolean hasOwner(long lecturerId){
        for (Lecturer lecturer : lecturers){
            if (lecturer.getId().equals(lecturerId)){
                return true;
            }
        }
        return false;
    }
}
