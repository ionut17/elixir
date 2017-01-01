package app.model.user;

import app.model.CustomGroupSerializer;
import app.model.CustomStudentSerializer;
import app.model.Group;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

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

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Group.class, cascade=CascadeType.ALL)
    @JoinTable(name = "GROUPS_STUDENTS",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") })
    @JsonSerialize(using = CustomGroupSerializer.class)
    private List<Group> groups = new ArrayList<Group>();

    //Constructors

    public Student(String password, String firstName, String lastName, String email){
        super(password, firstName, lastName, email);
    }

    public Student(){
    }

    //Setters and getters

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}
