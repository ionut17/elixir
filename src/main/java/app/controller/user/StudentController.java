package app.controller.user;

import app.controller.common.BaseController;
import app.model.Group;
import app.model.Pager;
import app.model.activity.ActivityAttendance;
import app.model.activity.ActivityFile;
import app.model.activity.ActivityGrade;
import app.model.dto.CourseDto;
import app.model.dto.StudentDto;
import app.model.dto.UserDto;
import app.service.GroupService;
import app.service.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController extends BaseController {

    @Autowired
    StudentService studentService;
    @Autowired
    GroupService groupService;

    //-------------------Retrieve All Students--------------------------------------------------------

    @RequestMapping(value = "/students", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity listAllStudents(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<StudentDto> students;
        if (query!=null){
            students = studentService.searchByPage(query, targetPage);
        } else{
            students = studentService.findAllByPage(targetPage);
        }
        if (students==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (students.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(students);
        toReturn.put("content", students.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //-------------------Retrieve Single Student--------------------------------------------------------

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") long id) {
        StudentDto student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @RequestMapping(value = "/students/email/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentDto> getStudentByEmail(@PathVariable("email") String email) {
        StudentDto student = studentService.findByEmail(email);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    //-------------------Retrieve Elements of Student--------------------------------------------------------

    @RequestMapping(value = "/students/{id}/attendances", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAttendancesOfStudent(@PathVariable("id") long id, @RequestParam("page") int page) {
        StudentDto student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityAttendance> attendances = studentService.getAttendances(student, page);
        if (attendances.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(attendances);
        toReturn.put("content", attendances.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{id}/grades", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGradesOfStudent(@PathVariable("id") long id, @RequestParam("page") int page) {
        StudentDto student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityGrade> grades = studentService.getGrades(student, page);
        if (grades.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(grades);
        toReturn.put("content", grades.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{id}/files", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getFilesOfStudent(@PathVariable("id") long id, @RequestParam("page") int page) {
        StudentDto student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String, Object> toReturn = new HashMap<>();
        Page<ActivityFile> files = studentService.getFiles(student, page);
        if (files.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(files);
        toReturn.put("content", files.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{id}/groups", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getGroupsOfStudent(@PathVariable("id") long id, @RequestParam("page") int page) {
        StudentDto student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String, Object> toReturn = new HashMap<>();
        Page<Group> groups = studentService.getGroups(student, page);
        if (groups.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(groups);
        toReturn.put("content", groups.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{id}/courses", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCoursesOfStudent(@PathVariable("id") long id, @RequestParam("page") int page) {
        StudentDto student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String, Object> toReturn = new HashMap<>();
        Page<CourseDto> courses = studentService.getCourses(student, page);
        if (courses.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(courses);
        toReturn.put("content", courses.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //-------------------Create a Student--------------------------------------------------------

    @RequestMapping(value = "/students", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createStudent(@RequestBody StudentDto student, UriComponentsBuilder ucBuilder) {
        if (studentService.entityExist(student)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        studentService.add(student);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/students/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //-------------------Add Group for Student---------------------------------------------

    @RequestMapping(value = "/students/{student_id}/groups", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> addGroupForStudent(@PathVariable("student_id") long student_id, @RequestBody Group group, UriComponentsBuilder ucBuilder) {
        StudentDto student = studentService.findById(student_id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Group foundGroup = groupService.findById(group.getId());
        if (foundGroup == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Group g : student.getGroups()) {
            if (g.getId().equals(foundGroup.getId())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }

        studentService.addGroupToStudent(foundGroup, student);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/students/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    //-------------------Update a Student--------------------------------------------------------

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") long id, @RequestBody StudentDto student) {
        StudentDto currentStudent = studentService.findById(id);

        if (currentStudent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentStudent.setFirstName(student.getFirstName());
        currentStudent.setLastName(student.getLastName());
        currentStudent.setEmail(student.getEmail());

        studentService.update(currentStudent);
        return new ResponseEntity<>(currentStudent, HttpStatus.OK);
    }

    //-------------------Delete a Student--------------------------------------------------------

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<StudentDto> deleteStudent(@PathVariable("id") long id) {
        StudentDto student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        studentService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //-------------------Delete Group of a Student--------------------------------------------------------

    @RequestMapping(value = "/students/{student_id}/groups/{group_id}", method = RequestMethod.DELETE)
    public ResponseEntity<StudentDto> deleteStudent(@PathVariable("student_id") long student_id, @PathVariable("group_id") long group_id) {
        StudentDto student = studentService.findById(student_id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Group group = groupService.findById(group_id);
        if (group == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.removeGroupOfStudent(group, student);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
