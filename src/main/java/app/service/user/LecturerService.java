package app.service.user;

import app.model.user.Lecturer;
import app.service.common.BaseService;

public interface LecturerService extends BaseService<Lecturer> {

    Lecturer findByEmail(String email);

}
