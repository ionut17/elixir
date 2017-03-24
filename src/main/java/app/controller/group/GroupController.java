package app.controller.group;

import app.controller.common.BaseController;
import app.model.Group;
import app.model.Pager;
import app.service.GroupService;
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
public class GroupController extends BaseController {

    @Autowired
    GroupService groupService;

    //-------------------Retrieve All Groups--------------------------------------------------------

    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity listAllGroups(@RequestParam(value="page", required = false) Integer page, @RequestParam(value="search", required = false) String query) {
        int targetPage = page!=null ? page.intValue() : 0;
        Map<String, Object> toReturn = new HashMap<>();
        Page<Group> groups;
        if (query!=null){
            groups = groupService.searchByPage(query, targetPage);
        } else{
            groups = groupService.findAllByPage(targetPage);
        }
        if (groups==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (groups.getSize() == 0) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Pager pager = new Pager(groups);
        toReturn.put("content", groups.getContent());
        toReturn.put("pager", pager);
        return new ResponseEntity(toReturn, HttpStatus.OK);
    }

    //-------------------Retrieve Single Group--------------------------------------------------------

    @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<Group> getGroupById(@PathVariable("id") long id) {
        Group group = groupService.findById(id);
        if (group == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/year/{year}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity<List<Group>> getGroupByYear(@PathVariable("year") int year) {
        List<Group> groups = groupService.findByYear(year);
        if (groups == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (groups.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    //-------------------Create a Group--------------------------------------------------------

    @RequestMapping(value = "/groups", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    ResponseEntity<Void> createGroup(@RequestBody Group group, UriComponentsBuilder ucBuilder) {
        if (groupService.entityExist(group)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        groupService.add(group);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/groups/{id}").buildAndExpand(group.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    //-------------------Update a Group--------------------------------------------------------

    @RequestMapping(value = "/groups/{id}", method = RequestMethod.PATCH)
    public
    @ResponseBody
    ResponseEntity<Group> updateGroup(@PathVariable("id") long id, @RequestBody Group group) {
        Group currentGroup = groupService.findById(id);

        if (currentGroup == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        currentGroup.setName(group.getName());
        currentGroup.setYear(group.getYear());

        groupService.update(currentGroup);
        return new ResponseEntity<>(currentGroup, HttpStatus.OK);
    }

    //-------------------Delete a User--------------------------------------------------------

    @RequestMapping(value = "/groups/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<Group> deleteGroup(@PathVariable("id") long id) {
        Group group = groupService.findById(id);
        if (group == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupService.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
