package app.controller.user;

import app.controller.common.BaseController;
import app.model.Pager;
import app.model.dto.CourseDto;
import app.model.dto.LecturerDto;
import app.model.user.Lecturer;
import app.service.user.LecturerService;
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
public class LecturerController extends BaseController {

    @Autowired
    LecturerService lecturerService;

    //-------------------Retrieve All Lecturers--------------------------------------------------------

    @RequestMapping(value = "/lecturers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAllLecturers(@RequestParam(value="page", required = false) Integer page, @RequestParam(value = "search", required = false) String query) {
        int targetPage = page != null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<LecturerDto> lecturers;
        if (query!=null){
            lecturers = lecturerService.searchByPage(query, targetPage);
        } else{
            lecturers = lecturerService.findAllByPage(targetPage);
        }
        if (lecturers==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (lecturers.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(lecturers);
        toReturn.put("content", lecturers.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //-------------------Retrieve Single Lecturer--------------------------------------------------------

    @RequestMapping(value = "/lecturers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LecturerDto> getLecturerById(@PathVariable("id") long id) {
        LecturerDto lecturer = lecturerService.findById(id);
        if (lecturer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lecturer, HttpStatus.OK);
    }

    @RequestMapping(value = "/lecturers/email/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LecturerDto> getLecturerByEmail(@PathVariable("email") String email) {
        LecturerDto lecturer = lecturerService.findByEmail(email);
        if (lecturer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lecturer, HttpStatus.OK);
    }

    //-------------------Retrieve Elements of Lecturer--------------------------------------------------------

    @RequestMapping(value = "/lecturers/{id}/courses", params={"page"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getCoursesOfLecturer(@PathVariable("id") long id, @RequestParam("page") int page) {
        LecturerDto lecturer = lecturerService.findById(id);
        if (lecturer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Map<String, Object> toReturn = new HashMap<>();
        Page<CourseDto> courses = lecturerService.getCourses(lecturer, page);
        if (courses.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(courses);
        toReturn.put("content", courses.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //-------------------Create a Lecturer--------------------------------------------------------

    @RequestMapping(value = "/lecturers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createLecturer(@RequestBody LecturerDto lecturer, UriComponentsBuilder ucBuilder) {
        if (lecturerService.entityExist(lecturer)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        lecturerService.add(lecturer);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/lecturers/{id}").buildAndExpand(lecturer.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    //-------------------Update a Lecturer--------------------------------------------------------

    @RequestMapping(value = "/lecturers/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<LecturerDto> updateLecturer(@PathVariable("id") long id, @RequestBody Lecturer lecturer) {
        LecturerDto currentLecturer = lecturerService.findById(id);

        if (currentLecturer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentLecturer.setFirstName(lecturer.getFirstName());
        currentLecturer.setLastName(lecturer.getLastName());
        currentLecturer.setEmail(lecturer.getEmail());

        lecturerService.update(currentLecturer);
        return new ResponseEntity<>(currentLecturer, HttpStatus.OK);
    }

    //-------------------Delete a Lecturer--------------------------------------------------------

    @RequestMapping(value = "/lecturers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<LecturerDto> deleteLecturer(@PathVariable("id") long id) {
        LecturerDto lecturer = lecturerService.findById(id);
        if (lecturer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        lecturerService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
