package app.service.user;

import app.model.Group;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityFile;
import app.model.activity.ActivityGrade;
import app.model.dto.CourseDto;
import app.model.dto.StudentDto;
import app.service.common.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService extends BaseService<StudentDto, Long> {

    StudentDto findByEmail(String email);

    Page<ActivityAttendance> getAttendances(StudentDto student, int page);

    Page<ActivityGrade> getGrades(StudentDto student, int page);

    Page<ActivityFile> getFiles(StudentDto student, int page);

    Page<Group> getGroups(StudentDto student, int page);

    Page<CourseDto> getCourses(StudentDto student, int page);

    StudentDto addGroupToStudent(Group group, StudentDto student);

    StudentDto removeGroupOfStudent(Group group, StudentDto student);

    Page<StudentDto> searchByPage(String query, int page);

}
