package app.service;

import app.model.Group;
import app.service.common.BaseService;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface GroupService extends BaseService<Group, Long> {

    List<Group> findByYear(int year);

    Group findByName(String name);

    void importGroupStudents(File importedFile) throws IOException;
}
