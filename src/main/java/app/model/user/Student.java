package app.model.user;

import app.model.Course;
import app.model.Group;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityFile;
import app.model.activity.ActivityGrade;
import app.model.serializer.CustomCourseListSerializer;
import app.model.serializer.CustomGroupListSerializer;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STUDENTS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id")
public class Student extends AbstractUser {

    //Fields

    @Column(name = "matricol", length = 50, unique = true)
    private String matricol;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Group.class, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "GROUPS_STUDENTS",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    @JsonSerialize(using = CustomGroupListSerializer.class)
    private List<Group> groups = new ArrayList<Group>();

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Course.class, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "COURSE_ATTENDANTS",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    @JsonSerialize(using = CustomCourseListSerializer.class)
    private List<Course> courses = new ArrayList<Course>();

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, targetEntity = ActivityAttendance.class, orphanRemoval = true, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE})
    @Fetch(value = FetchMode.SUBSELECT)
//    @JsonSerialize(using = CustomActivityAttendanceListSerializer.class)
    private List<ActivityAttendance> attendances = new ArrayList<ActivityAttendance>();

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, targetEntity = ActivityGrade.class, orphanRemoval = true, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE})
    @Fetch(value = FetchMode.SUBSELECT)
//    @JsonSerialize(using = CustomActivityGradeListSerializer.class)
    private List<ActivityGrade> grades = new ArrayList<ActivityGrade>();

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, targetEntity = ActivityFile.class, orphanRemoval = true, cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE})
    @Fetch(value = FetchMode.SUBSELECT)
//    @JsonSerialize(using = CustomActivityFileListSerializer.class)
    private List<ActivityFile> files = new ArrayList<>();

    //Constructors

    public Student(String password, String firstName, String lastName, String email) {
        super(password, firstName, lastName, email);
    }

    public Student() {
    }

    //Setters and getters

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<ActivityAttendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<ActivityAttendance> attendances) {
        this.attendances = attendances;
    }

    public List<ActivityGrade> getGrades() {
        return grades;
    }

    public void setGrades(List<ActivityGrade> grades) {
        this.grades = grades;
    }

    public List<ActivityFile> getFiles() {
        return files;
    }

    public void setFiles(List<ActivityFile> files) {
        this.files = files;
    }

    public String getMatricol() {
        return matricol;
    }

    public void setMatricol(String matricol) {
        this.matricol = matricol;
    }
}
