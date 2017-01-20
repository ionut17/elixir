package app.model.user;

import app.model.Course;
import app.model.Group;
import app.model.activity.ActivityAttendance;
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

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Group.class, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "GROUPS_STUDENTS",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    @JsonSerialize(using = CustomGroupListSerializer.class)
    private List<Group> groups = new ArrayList<Group>();

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Course.class, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "COURSE_ATTENDANTS",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    @JsonSerialize(using = CustomCourseListSerializer.class)
    private List<Course> courses = new ArrayList<Course>();

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, targetEntity = ActivityAttendance.class, cascade = CascadeType.ALL)
    private List<ActivityAttendance> attendances;

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
}
