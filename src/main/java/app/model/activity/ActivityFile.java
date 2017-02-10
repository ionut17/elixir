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
@Table(name = "ACTIVITY_FILES")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class ActivityFile implements Item, Serializable {

    @EmbeddedId
    private ActivityFileId id;

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

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "file_id", nullable = false)
    private Long fileId;

    public ActivityFile() {
    }

    public ActivityFileId getId() {
        return id;
    }

    public void setId(ActivityFileId id) {
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


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}

