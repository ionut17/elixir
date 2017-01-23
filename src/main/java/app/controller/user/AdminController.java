package app.controller.user;

import app.controller.common.BaseController;
import app.model.user.Admin;
import app.service.user.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class AdminController extends BaseController {

    @Autowired
    AdminService adminService;

    //-------------------Retrieve All Admins--------------------------------------------------------

    @RequestMapping(value = "/admins", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Admin>> listAllAdmins() {
        List<Admin> admins = adminService.findAll();
        if (admins.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    //-------------------Retrieve Single Admin--------------------------------------------------------

    @RequestMapping(value = "/admins/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") long id) {
        Admin admin = adminService.findById(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @RequestMapping(value = "/admins/email/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable("email") String email) {
        Admin admin = adminService.findByEmail(email);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    //-------------------Create a Admin--------------------------------------------------------

    @RequestMapping(value = "/admins", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createAdmin(@RequestBody Admin admin, UriComponentsBuilder ucBuilder) {
        if (adminService.entityExist(admin)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        adminService.add(admin);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/admins/{id}").buildAndExpand(admin.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }


    //-------------------Update a Admin--------------------------------------------------------

    @RequestMapping(value = "/admins/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Admin> updateAdmin(@PathVariable("id") long id, @RequestBody Admin admin) {
        Admin currentAdmin = adminService.findById(id);

        if (currentAdmin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentAdmin.setFirstName(admin.getFirstName());
        currentAdmin.setLastName(admin.getLastName());
        currentAdmin.setEmail(admin.getEmail());

        adminService.update(currentAdmin);
        return new ResponseEntity<>(currentAdmin, HttpStatus.OK);
    }

    //-------------------Delete a Admin--------------------------------------------------------

    @RequestMapping(value = "/admins/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Admin> deleteAdmin(@PathVariable("id") long id) {
        Admin admin = adminService.findById(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adminService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
