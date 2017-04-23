package app.controller.course;

import app.auth.JwtAuthenticatedUser;
import app.controller.common.BaseController;
import app.model.Course;
import app.model.Pager;
import app.model.activity.Activity;
import app.model.dto.CourseDto;
import app.model.dto.LecturerDto;
import app.model.dto.StudentDto;
import app.model.user.User;
import app.service.CourseService;
import app.service.user.LecturerService;
import app.service.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CourseController extends BaseController {

    @Autowired
    CourseService courseService;
    @Autowired
    LecturerService lecturerService;
    @Autowired
    StudentService studentService;

    //-------------------Retrieve All Courses--------------------------------------------------------

    @RequestMapping(value = "/courses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllCourses(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<CourseDto> courses;
        if (query!=null){
            courses = courseService.searchByPage(query, targetPage);
        } else{
            courses = courseService.findAllByPage(targetPage);
        }
        if (courses==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (courses.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(courses);
        toReturn.put("content", courses.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllCoursesUnpaged() {
        List<CourseDto> courses = courseService.findAll();
        if (courses==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (courses.size() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(courses, HttpStatus.OK);
    }

    //-------------------Retrieve Single Course--------------------------------------------------------

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<CourseDto> getCourseById(@PathVariable("id") long id) {
        CourseDto course = courseService.findById(id);
        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/year/{year}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<List<CourseDto>> getCoursesByYear(@PathVariable("year") int year) {
        List<CourseDto> courses = courseService.findByYear(year);
        if (courses == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (courses.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    //-------------------Retrieve Elements of Course--------------------------------------------------------

    @RequestMapping(value = "/courses/{id}/lecturers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getLecturersOfCourse(@PathVariable("id") long id, @RequestParam(value="page", required = false) Integer page) {

        Map<String, Object> toReturn = new HashMap<>();

        CourseDto course = courseService.findById(id);
        if (course == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        int targetPage = page!=null? page.intValue() : 0;
        Page<LecturerDto> lecturers = courseService.getLecturers(course, targetPage);
        if (lecturers.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(lecturers);
        toReturn.put("content", lecturers.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //Add lecturers to course
    @RequestMapping(value = "/courses/{course_id}/lecturers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addLecturersToCourse(@PathVariable("course_id") Long courseId, @RequestBody Map<String, Long> lecturerDetails) {
        CourseDto course = courseService.findById(courseId);
        LecturerDto lecturer = lecturerService.findById(lecturerDetails.get("lecturer_id"));
        if (course==null || lecturer==null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        LecturerDto resultLecturer = lecturerService.addCourseToLecturer(course.getId(), lecturer.getId());
        return new ResponseEntity(resultLecturer, HttpStatus.CREATED);
    }

    //Add students to course
    @RequestMapping(value = "/courses/{course_id}/students", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addStudentsToCourse(@PathVariable("course_id") Long courseId, @RequestBody Map<String, List<Long>> studentDetails) {
        List<StudentDto> addedStudents = new ArrayList<>();
        for (Long studentId : studentDetails.get("student_ids")){
            StudentDto student = studentService.addCourseToStudent(courseId, studentId);
            if (student==null){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            addedStudents.add(student);
        }
        return new ResponseEntity(addedStudents, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/courses/{id}/students", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getStudentsOfCourse(@PathVariable("id") long id, @RequestParam(value="page", required = false) Integer page) {

        Map<String, Object> toReturn = new HashMap<>();

        CourseDto course = courseService.findById(id);
        if (course == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        int targetPage = page!=null? page.intValue() : 0;
        Page<StudentDto> students = courseService.getStudents(course, targetPage);
        if (students.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(students);
        toReturn.put("content", students.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/{id}/activities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getActivitiesOfCourse(@PathVariable("id") long id, @RequestParam(value="page", required = false) Integer page) {

        Map<String, Object> toReturn = new HashMap<>();

        CourseDto course = courseService.findById(id);
        if (course == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        int targetPage = page!=null? page.intValue() : 0;
        Page<Activity> activities = courseService.getActivities(course, targetPage);
        if (activities.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(activities);
        toReturn.put("content", activities.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //-------------------Create a Course--------------------------------------------------------

    @RequestMapping(value = "/courses", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    ResponseEntity<Void> createCourse(@RequestBody CourseDto course, UriComponentsBuilder ucBuilder) {
        if (courseService.entityExist(course)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        courseService.add(course);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/courses/{id}").buildAndExpand(course.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //-------------------Update a Course--------------------------------------------------------

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.PATCH)
    public
    @ResponseBody
    ResponseEntity<CourseDto> updateCourse(@PathVariable("id") long id, @RequestBody CourseDto course) {
        CourseDto currentCourse = courseService.findById(id);

        if (currentCourse == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentCourse.setTitle(course.getTitle());
        currentCourse.setYear(course.getYear());

        courseService.update(currentCourse);
        return new ResponseEntity<>(currentCourse, HttpStatus.OK);
    }

    //-------------------Delete a Course--------------------------------------------------------

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<Course> deleteCourse(@PathVariable("id") long id) {
        CourseDto course = courseService.findById(id);
        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        courseService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
