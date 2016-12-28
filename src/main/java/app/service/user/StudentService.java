package app.service.user;

import app.model.user.Student;
import app.service.common.BaseService;

public interface StudentService extends BaseService<Student> {

    Student findByEmail(String email);

}
