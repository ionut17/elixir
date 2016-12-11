package app.service;

import app.model.Group;
import app.model.User;
import app.repository.GroupRepository;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component("groupService")
public class GroupServiceImpl implements GroupService{

    @Autowired
    GroupRepository groups;

    public GroupServiceImpl(){

    }

    @Override
    public List<Group> listGroups(){
        return groups.findAll();
    }

    @Override
    public Group addGroup(String name, int yearOfStudy) {
        return groups.save(new Group(name, yearOfStudy));
    }

    @Override
    public String removeGroup(Long id) {
        try{
            groups.delete(id);
            return "Group with id "+id+" has been removed!";
        } catch (Exception ex){
            return ex.toString();
        }
    }

}
