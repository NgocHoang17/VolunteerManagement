package vn.edu.volunteer.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.volunteer.model.User;

public interface UserService extends UserDetailsService {
    User findByUsername(String username);
    User findByEmail(String email);
    User save(User user);
    void deleteByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findRecentUsers(int limit);
    long count();
}