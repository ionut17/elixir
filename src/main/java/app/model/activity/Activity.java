package app.model.activity;

import app.model.Course;
import app.model.common.Item;
import app.model.serializer.CustomActivitySerializer;
import app.model.serializer.CustomCourseSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ACTIVITIES")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Activity implements Item, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd' 'hh:mm:ss") //yyyy-MM-dd'T'hh:mm:ss.SSSZ
    private Date date;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ActivityType type;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @JsonSerialize(using = CustomCourseSerializer.class)
    private Course course;

//    @OneToMany(mappedBy = "activity", fetch = FetchType.EAGER, targetEntity = ActivityAttendance.class, cascade = CascadeType.ALL)
//    private List<ActivityAttendance> attendances;

    public Activity() {
    }

    //    protected Activity() {
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
