package app.service;

import app.model.Group;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface GroupService extends BaseService<Group>{

    List<Group> findByYearOfStudy(int year);

}
