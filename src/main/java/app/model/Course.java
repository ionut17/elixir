package app.model;

import app.model.common.Item;
import app.model.serializer.CustomLecturerSerializer;
import app.model.serializer.CustomStudentSerializer;
import app.model.user.Lecturer;
import app.model.user.Student;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, targetEntity = Lecturer.class, cascade=CascadeType.ALL)
    @JsonSerialize(using = CustomLecturerSerializer.class)
    private List<Lecturer> lecturers;

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
}
