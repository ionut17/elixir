package app.service;

import app.model.Group;
import app.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("groupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groups;

    public GroupServiceImpl() {

    }

    @Override
    public List<Group> findAll() {
        return groups.findAll();
    }

    @Override
    public Group findById(long id) {
        return groups.findOne(id);
    }

    @Override
    public List<Group> findByYear(int year) {
        return groups.findByYear(year);
    }

    @Override
    public Group findByName(String name) {
        return groups.findByName(name);
    }

    @Override
    public Group add(Group entity) {
        return groups.save(entity);
    }

    @Override
    public Group update(Group user) {
        return null;
    }

    @Override
    public void remove(Long id) {
        groups.delete(id);
    }

    @Override
    public boolean entityExist(Group entity) {
        Group found = groups.findByName(entity.getName());
        if (found == null) {
            return false;
        }
        return true;
    }

}
