package app.controller;

import app.model.user.Lecturer;
import app.service.user.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class LecturerController {

    @Autowired
    LecturerService lecturerService;

    //-------------------Retrieve All Lecturers--------------------------------------------------------

    @RequestMapping(value = "/lecturers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Lecturer>> listAllLecturers() {
        List<Lecturer> lecturers = lecturerService.findAll();
        if (lecturers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(lecturers, HttpStatus.OK);
    }

    //-------------------Retrieve Single Lecturer--------------------------------------------------------

    @RequestMapping(value = "/lecturers/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lecturer> getLecturerById(@PathVariable("id") int id) {
        Lecturer lecturer = lecturerService.findById(id);
        if (lecturer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lecturer, HttpStatus.OK);
    }

    @RequestMapping(value = "/lecturers/email/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lecturer> getLecturerByEmail(@PathVariable("email") String email) {
        Lecturer lecturer = lecturerService.findByEmail(email);
        if (lecturer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lecturer, HttpStatus.OK);
    }

    //-------------------Create a Lecturer--------------------------------------------------------

    @RequestMapping(value = "/lecturers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createLecturer(@RequestBody Lecturer lecturer, UriComponentsBuilder ucBuilder) {
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
    public ResponseEntity<Lecturer> updateLecturer(@PathVariable("id") long id, @RequestBody Lecturer lecturer) {
        Lecturer currentLecturer = lecturerService.findById(id);

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
    public ResponseEntity<Lecturer> deleteLecturer(@PathVariable("id") long id) {
        Lecturer lecturer = lecturerService.findById(id);
        if (lecturer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        lecturerService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
