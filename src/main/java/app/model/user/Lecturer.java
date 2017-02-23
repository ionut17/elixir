package app.model.user;

import app.model.Course;
import app.model.serializer.CustomCourseListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LECTURERS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Lecturer extends AbstractUser {

    //Fields

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Course.class, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "COURSE_OWNERSHIPS",
            joinColumns = {@JoinColumn(name = "lecturer_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    @JsonSerialize(using = CustomCourseListSerializer.class)
    private List<Course> courses = new ArrayList<>();

    //Constructors

    public Lecturer(String password, String firstName, String lastName, String email) {
        super(password, firstName, lastName, email);
    }

    public Lecturer() {
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public boolean hasCourse(Course course) {
        return courses.contains(course);
    }
}
