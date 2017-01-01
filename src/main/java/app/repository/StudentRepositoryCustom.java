package app.repository;

import app.model.Group;
import app.model.user.Student;

/**
 * Created by Ionut on 31-Dec-16.
 */
public interface StudentRepositoryCustom {

    Student addGroupToStudent(Group group, Student student);

    Student removeGroupOfStudent(Group group, Student student);

}
