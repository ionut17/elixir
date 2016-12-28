package app.service;

import app.model.Group;
import app.service.common.BaseService;

import java.util.List;

public interface GroupService extends BaseService<Group> {

    List<Group> findByYearOfStudy(int year);

}
