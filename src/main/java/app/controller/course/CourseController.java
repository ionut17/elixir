package app.controller.course;

import app.controller.common.BaseController;
import app.model.Course;
import app.service.CourseService;
import app.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class CourseController extends BaseController {

    @Autowired
    CourseService courseService;

    //-------------------Retrieve All Courses--------------------------------------------------------

    @RequestMapping(value = "/courses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Course>> listAllCourses() {
        List<Course> courses = courseService.findAll();
        if (courses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    //-------------------Retrieve Single Course--------------------------------------------------------

    @RequestMapping(value = "/courses/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Course> getCourseById(@PathVariable("id") int id) {
        Course course = courseService.findById(id);
        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/year/{year}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<Course>> getCoursesByYear(@PathVariable("year") int year) {
        List<Course> courses = courseService.findByYear(year);
        if (courses == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (courses.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    //-------------------Create a Course--------------------------------------------------------

    @RequestMapping(value = "/courses", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResponseEntity<Void> createCourse(@RequestBody Course course, UriComponentsBuilder ucBuilder) {
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
    public @ResponseBody ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody Course course) {
        Course currentCourse = courseService.findById(id);

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
    public @ResponseBody ResponseEntity<Course> deleteCourse(@PathVariable("id") long id) {
        Course course = courseService.findById(id);
        if (course == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        courseService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
