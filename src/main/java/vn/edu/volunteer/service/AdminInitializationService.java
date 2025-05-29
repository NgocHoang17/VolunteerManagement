package vn.edu.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.User;

@Service
public class AdminInitializationService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRED)
    public void initializeAdminUser() {
        if (!userService.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("12345678"));
            admin.setEmail("admin@localhost");
            admin.setFullName("Administrator");
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);
            userService.save(admin);
        }
    }
} 