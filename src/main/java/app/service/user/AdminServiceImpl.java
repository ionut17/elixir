package app.service.user;

import app.model.user.Admin;
import app.repository.AdminRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("adminService")
public class AdminServiceImpl implements AdminService {

    //Main repository
    @Autowired
    private AdminRepository admins;

    //Other dependencies
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Logger logger;

    public AdminServiceImpl() {
    }

    @Override
    public List<Admin> findAll() {
        return admins.findAll();
    }

    @Override
    public Admin findById(long id) {
        return admins.findOne(id);
    }

    @Override
    public Admin add(Admin admin) {
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
//        logger.info(encodedPassword);
        Admin newAdmin = new Admin(encodedPassword, admin.getFirstName(), admin.getLastName(), admin.getEmail());
        return admins.save(newAdmin);
    }

    @Override
    public Admin update(Admin admin) {
        return null;
    }

    @Override
    public void remove(Long id) {
        admins.delete(id);
    }

    @Override
    public boolean entityExist(Admin admin) {
        Admin found = admins.findByEmail(admin.getEmail());
        if (found == null) {
            return false;
        }
        return true;
    }

    @Override
    public Admin findByEmail(String email) {
        return admins.findByEmail(email);
    }

}
