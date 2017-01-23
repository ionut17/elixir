package app.model.activity;

import app.model.common.Item;
import app.model.serializer.CustomActivitySerializer;
import app.model.serializer.CustomStudentSerializer;
import app.model.user.Student;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ACTIVITY_GRADES")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ActivityGrade implements Item, Serializable {

    @EmbeddedId
    private ActivityGradeId id;

    @MapsId("studentId")
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    @JsonSerialize(using = CustomStudentSerializer.class)
    private Student student;

    @MapsId("activityId")
    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false)
    @JsonSerialize(using = CustomActivitySerializer.class)
    private Activity activity;

    @Column(name = "value", nullable = false, length = 3)
    private int value;

    public ActivityGrade() {
    }

    public ActivityGradeId getId() {
        return id;
    }

    public void setId(ActivityGradeId id) {
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

