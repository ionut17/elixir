package app.service.user;

import app.model.Group;
import app.model.user.Student;
import app.service.common.BaseService;

public interface StudentService extends BaseService<Student> {

    Student findByEmail(String email);

    Student addGroupToStudent(Group group, Student student);

    Student removeGroupOfStudent(Group group, Student student);
}
