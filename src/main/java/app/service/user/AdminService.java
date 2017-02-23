package app.service.user;

import app.model.user.Admin;
import app.service.common.BaseService;
import org.springframework.data.domain.Page;

public interface AdminService extends BaseService<Admin, Long> {

    Admin findByEmail(String email);

    Page<Admin> searchByPage(String query, int page);

}
