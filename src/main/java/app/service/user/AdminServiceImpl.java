package app.service.user;

import app.model.dto.AdminDto;
import app.model.user.Admin;
import app.repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Type;
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
    private ModelMapper modelMapper;
    @Autowired
    private Logger logger;

    public AdminServiceImpl() {
    }

    @Override
    public List<AdminDto> findAll() {
        Type listType = new TypeToken<List<AdminDto>>() {}.getType();
        return modelMapper.map(admins.findAll(), listType);
    }

    @Override
    public Page<AdminDto> findAllByPage(int page) {
        Type listType = new TypeToken<Page<AdminDto>>() {}.getType();
        return modelMapper.map(admins.findAll(new PageRequest(page, 10, Sort.Direction.ASC, "lastName")), listType);
    }

    @Override
    public AdminDto findById(Long id) {
        return modelMapper.map(admins.findOne(id), AdminDto.class);
    }

    @Override
    public AdminDto add(AdminDto admin) {
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
//        logger.info(encodedPassword);
        Admin newAdmin = new Admin(encodedPassword, admin.getFirstName(), admin.getLastName(), admin.getEmail());
        return modelMapper.map(admins.save(newAdmin), AdminDto.class);
    }

    @Override
    public AdminDto update(AdminDto admin) {
        return null;
    }

    @Override
    public void remove(Long id) {
        admins.delete(id);
    }

    @Override
    public boolean entityExist(AdminDto admin) {
        Admin found = admins.findByEmail(admin.getEmail());
        if (found == null) {
            return false;
        }
        return true;
    }

    @Override
    public List<AdminDto> importEntities(File file) {
        return null;
    }

    @Override
    public AdminDto findByEmail(String email) {
        return modelMapper.map(admins.findByEmail(email), AdminDto.class);
    }

    @Override
    public Page<AdminDto> searchByPage(String query, int page) {
        Type listType = new TypeToken<Page<AdminDto>>() {}.getType();
        return modelMapper.map(admins.findDistinctByFirstNameOrLastNameOrEmailAllIgnoreCaseContaining(query, query, query, new PageRequest(page, 10)), listType);
    }

}
