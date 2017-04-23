package app.repository;

import app.model.Course;
import app.model.Group;
import app.model.user.Lecturer;
import app.model.user.Student;

/**
 * Created by Ionut on 31-Dec-16.
 */
public interface LecturerRepositoryCustom {

    Lecturer addCourseToLecturer(Course course, Lecturer lecturer);

    Lecturer removeCourseOfLecturer(Course course, Lecturer lecturer);

}
