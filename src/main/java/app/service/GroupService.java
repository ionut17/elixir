package app.service;

import app.model.Group;
import app.service.common.BaseService;

import java.util.List;

public interface GroupService extends BaseService<Group, Long> {

    List<Group> findByYear(int year);

    Group findByName(String name);
}
