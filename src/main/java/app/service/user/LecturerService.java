package app.service.user;

import app.model.dto.CourseDto;
import app.model.dto.LecturerDto;
import app.model.dto.StudentDto;
import app.model.user.Lecturer;
import app.service.common.BaseService;
import org.springframework.data.domain.Page;

public interface LecturerService extends BaseService<LecturerDto, Long> {

    LecturerDto findByEmail(String email);

    Page<CourseDto> getCourses(LecturerDto lecturer, int page);

    Page<LecturerDto> searchByPage(String query, int page);

}
