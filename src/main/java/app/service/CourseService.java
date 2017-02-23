package app.service;

import app.model.Course;
import app.model.activity.Activity;
import app.model.dto.CourseDto;
import app.model.dto.LecturerDto;
import app.model.dto.StudentDto;
import app.service.common.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService extends BaseService<CourseDto, Long> {

    CourseDto findByTitle(String title);

    List<CourseDto> findByYear(int year);

    List<CourseDto> findBySemester(int semester);

    Page<LecturerDto> getLecturers(CourseDto course, int page);

    Page<StudentDto> getStudents(CourseDto course, int page);

    Page<Activity> getActivities(CourseDto course, int page);

}
