package app.controller;

import app.model.Group;
import app.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class GroupController {

    @Autowired
    GroupService groups;

    @RequestMapping(value = "/groups", method = GET)
    public List<Group> listGroups() {
        return groups.listGroups();
    }

    @RequestMapping(value = "/groups/delete", method = POST)
    public String removeUser(@RequestParam("id") Long id) {
        return groups.removeGroup(id);
    }

}
