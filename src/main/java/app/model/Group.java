package app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="GROUPS")
public class Group implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="year_of_study", nullable = false, length=1)
    private int yearOfStudy;

    public Group(String name, int yearOfStudy) {
        this.setName(name);
        this.setYearOfStudy(yearOfStudy);
    }

    protected Group(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

}
