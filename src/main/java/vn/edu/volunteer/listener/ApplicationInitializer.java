package vn.edu.volunteer.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.edu.volunteer.model.User;
import vn.edu.volunteer.service.UserService;

@Component
public class ApplicationInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Kiểm tra và tạo tài khoản admin mặc định nếu chưa tồn tại
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