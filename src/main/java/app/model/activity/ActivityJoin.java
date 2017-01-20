package app.model.activity;

import app.model.Course;
import app.model.common.Item;
import app.model.serializer.CustomActivitySerializer;
import app.model.serializer.CustomCourseSerializer;
import app.model.serializer.CustomStudentSerializer;
import app.model.user.Student;
import app.model.user.User;
import app.model.user.UserId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Where;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;

@Primary
@Entity
@Table(name = "ACTIVITIES_JOIN")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public final class ActivityJoin implements Item, Serializable {

    //Fields

    @EmbeddedId
    private ActivityJoinId id;


    //TODO refactorizare user (primary key composite) / rescriere script creare / utilizare id composite in join
    @MapsId("userId")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            @JoinColumn(name = "user_type", referencedColumnName = "type", nullable = false)
    })
//    @JsonSerialize(using = CustomStudentSerializer.class)
    private User user;

    @MapsId("activityId")
    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false)
    @JsonSerialize(using = CustomActivitySerializer.class)
    private Activity activity;

    @MapsId("courseId")
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    @JsonSerialize(using = CustomCourseSerializer.class)
    private Course course;

    //Constructors

    public ActivityJoin() {
    }

    //Getters and Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}

@Embeddable
class ActivityJoinId implements Serializable {

    private UserId userId;
    private Long activityId;
    private Long courseId;

}