package app.model;

import app.model.common.Item;
import app.model.serializer.CustomStudentListSerializer;
import app.model.user.Student;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "GROUPS")
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Group implements Item, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "year", nullable = false, length = 1)
    private int year;

    @ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER, targetEntity = Student.class, cascade = CascadeType.ALL)
    @JsonSerialize(using = CustomStudentListSerializer.class)
    private List<Student> students;

    public Group(String name, int yearOfStudy) {
        this.setName(name);
        this.setYear(yearOfStudy);
    }

    protected Group() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int yearOfStudy) {
        this.year = yearOfStudy;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
