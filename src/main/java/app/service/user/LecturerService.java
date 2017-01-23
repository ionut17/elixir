package app.service.user;

import app.model.dto.LecturerDto;
import app.model.user.Lecturer;
import app.service.common.BaseService;

public interface LecturerService extends BaseService<LecturerDto, Long> {

    LecturerDto findByEmail(String email);

}
