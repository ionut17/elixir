package app.service.user;

import app.model.Group;
import app.model.dto.StudentDto;
import app.service.common.BaseService;

public interface StudentService extends BaseService<StudentDto, Long> {

    StudentDto findByEmail(String email);

    StudentDto addGroupToStudent(Group group, StudentDto student);

    StudentDto removeGroupOfStudent(Group group, StudentDto student);
}
