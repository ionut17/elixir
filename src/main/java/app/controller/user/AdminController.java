package app.controller.user;

import app.controller.common.BaseController;
import app.model.Pager;
import app.model.dto.AdminDto;
import app.model.user.Admin;
import app.service.user.AdminService;
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
public class AdminController extends BaseController {

    @Autowired
    AdminService adminService;

    //-------------------Retrieve All Admins--------------------------------------------------------

    @RequestMapping(value = "/admins", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity listAllAdmins(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "search", required = false) String query) {
        int targetPage = page != null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<AdminDto> admins;
        if (query!=null){
            admins = adminService.searchByPage(query, targetPage);
        } else{
            admins = adminService.findAllByPage(targetPage);
        }
        if (admins==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (admins.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(admins);
        toReturn.put("content", admins.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //-------------------Retrieve Single Admin--------------------------------------------------------

    @RequestMapping(value = "/admins/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAdminById(@PathVariable("id") long id) {
        AdminDto admin = adminService.findById(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @RequestMapping(value = "/admins/email/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAdminByEmail(@PathVariable("email") String email) {
        AdminDto admin = adminService.findByEmail(email);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    //-------------------Create a Admin--------------------------------------------------------

    @RequestMapping(value = "/admins", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> createAdmin(@RequestBody AdminDto admin, UriComponentsBuilder ucBuilder) {
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
    public ResponseEntity updateAdmin(@PathVariable("id") long id, @RequestBody Admin admin) {
        AdminDto currentAdmin = adminService.findById(id);

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
    public ResponseEntity deleteAdmin(@PathVariable("id") long id) {
        AdminDto admin = adminService.findById(id);
        if (admin == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        adminService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
