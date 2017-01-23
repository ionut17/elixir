package app.service;

import app.model.Course;
import app.service.common.BaseService;

import java.util.List;

public interface CourseService extends BaseService<Course, Long> {

    Course findByTitle(String title);

    List<Course> findByYear(int year);

    List<Course> findBySemester(int semester);

}
