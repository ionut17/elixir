package app.controller.user;

import app.controller.common.BaseController;
import app.model.Group;
import app.model.dto.StudentDto;
import app.service.GroupService;
import app.service.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class StudentController extends BaseController {

    @Autowired
    StudentService studentService;
    @Autowired
    GroupService groupService;

    //-------------------Retrieve All Students--------------------------------------------------------

    @RequestMapping(value = "/students", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<StudentDto>> listAllStudents() {
        List<StudentDto> students = studentService.findAll();
        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
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

    //-------------------Retrieve Groups of Student--------------------------------------------------------

    @RequestMapping(value = "/students/{id}/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Group>> getGroupsOfStudent(@PathVariable("id") long id) {
        StudentDto student = studentService.findById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student.getGroups(), HttpStatus.OK);
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
