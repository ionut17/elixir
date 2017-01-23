package app.service.user;

import app.model.user.Admin;
import app.service.common.BaseService;

public interface AdminService extends BaseService<Admin, Long> {

    Admin findByEmail(String email);

}
