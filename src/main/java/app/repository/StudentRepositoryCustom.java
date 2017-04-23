package app.repository;

import app.model.Course;
import app.model.Group;
import app.model.user.Student;

import java.util.List;

/**
 * Created by Ionut on 31-Dec-16.
 */
public interface StudentRepositoryCustom {

    Student addGroupToStudent(Group group, Student student);

    Student removeGroupOfStudent(Group group, Student student);

    Student addCourseToStudent(Course course, Student student);

    Student removeCourseFromStudent(Course course, Student student);

}
