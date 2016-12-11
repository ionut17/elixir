package app.service;

import app.model.Group;

import java.util.List;

public interface GroupService {

    public List<Group> listGroups();

    public Group addGroup(String name, int yearOfStudy);

    public String removeGroup(Long id);

}
