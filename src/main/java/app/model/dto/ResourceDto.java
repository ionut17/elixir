package app.model.dto;

import app.model.Course;
import app.model.DbSnapshot;
import app.model.Group;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityFile;
import app.model.activity.ActivityGrade;
import app.model.common.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class ResourceDto implements Item {

    private String snapshot;

    private Long courseId;

    private Long typeId;

    private String name;

    private Long studentId;

    private String fileName;

    private String extension;

    //Constructors

    public ResourceDto() {
    }

    public ResourceDto(ActivityFile activityFile, DbSnapshot snapshot){
        this.setCourseId(activityFile.getActivity().getCourse().getId());
        this.setTypeId(activityFile.getActivity().getType().getId());
        this.setName(activityFile.getActivity().getName());
        this.setStudentId(activityFile.getStudent().getId());
        this.setFileName(activityFile.getFileName());
        this.setExtension(activityFile.getExtension());
        this.setSnapshot(snapshot.getKey());
    }

    public ResourceDto(Long courseId, Long typeId, String name, Long studentId, String fileName) {
        this.setCourseId(courseId);
        this.setTypeId(typeId);
        this.setName(name);
        this.setStudentId(studentId);
        this.setFileName(fileName);
    }

    public ResourceDto(Long courseId, Long typeId, String name, Long studentId, String fileName, String extension) {
        this.setCourseId(courseId);
        this.setTypeId(typeId);
        this.setName(name);
        this.setStudentId(studentId);
        this.setFileName(fileName);
        this.setExtension(extension);
    }

    //Setters and getters

    public String toRelativePath(){
        String endOfString = "";
        if (this.getExtension()!=null){
            endOfString = '.'+this.getExtension();
        }
        String[] pathDetails = {this.getSnapshot(), String.valueOf(this.getCourseId()), String.valueOf(this.getTypeId()), this.getName(), String.valueOf(this.getStudentId()), this.getFileName()+endOfString};
        return String.join("/", pathDetails);
    }

    public boolean isValid(){
        return this.getCourseId()!=null && this.getTypeId()!=null && this.getName()!=null && this.getStudentId()!=null && this.getFileName()!=null;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String filename) {
        this.fileName = filename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }
}
