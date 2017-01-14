package app.model.activity;

import app.model.common.Item;
import app.model.user.Student;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ACTIVITY_ATTENDANCES")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ActivityAttendance implements Item, Serializable {

    @EmbeddedId
    private ActivityAttendanceId id;

    @MapsId("studentId")
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    private Student student;

    @MapsId("activityId")
    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false)
    private Activity activity;

    public ActivityAttendance() {
    }

    public ActivityAttendanceId getId() {
        return id;
    }

    public void setId(ActivityAttendanceId id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}

@Embeddable
class ActivityAttendanceId implements Serializable {

    private Long studentId;
    private Long activityId;

}