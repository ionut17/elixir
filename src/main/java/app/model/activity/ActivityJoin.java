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

    public String getRole() {
        return id.getRole();
    }

    public void setRole(String role) {
        this.id.setRole(role);
    }
}

@Embeddable
class ActivityJoinId implements Serializable {

    private UserId userId;
    private Long activityId;

    @Column(name = "role", nullable = false, length = 100)
    private String role;

    //Constructors

    public ActivityJoinId(){}

    public ActivityJoinId(String role){
        this.setRole(role);
    }

    //Getters and setters

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}