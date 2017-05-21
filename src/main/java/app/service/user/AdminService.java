package app.service.user;

import app.model.dto.AdminDto;
import app.model.user.Admin;
import app.service.common.BaseService;
import org.springframework.data.domain.Page;

public interface AdminService extends BaseService<AdminDto, Long> {

    AdminDto findByEmail(String email);

}
