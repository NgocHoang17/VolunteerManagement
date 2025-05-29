package vn.edu.volunteer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Primary;
import vn.edu.volunteer.model.User;
import vn.edu.volunteer.repository.UserRepository;
import vn.edu.volunteer.service.UserService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.volunteer.model.ToChuc;
import vn.edu.volunteer.model.SinhVien;

@Service("userService")
@Primary
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng: " + username));

        if (!user.isEnabled()) {
            throw new UsernameNotFoundException("Tài khoản đã bị vô hiệu hóa: " + username);
        }

        if (user.isBlocked()) {
            throw new UsernameNotFoundException("Tài khoản đã bị khóa: " + username);
        }

        // Kiểm tra và load thông tin theo role
        if (user.getRole() != null) {
            if (user.getRole().equals("ROLE_ORGANIZATION")) {
                // Load thông tin tổ chức
                ToChuc toChuc = user.getToChuc();
                if (toChuc == null) {
                    logger.warn("Tài khoản tổ chức {} chưa có thông tin tổ chức", username);
                }
            } else if (user.getRole().equals("ROLE_STUDENT")) {
                // Load thông tin sinh viên
                SinhVien sinhVien = user.getSinhVien();
                if (sinhVien == null) {
                    logger.warn("Tài khoản sinh viên {} chưa có thông tin sinh viên", username);
                }
            }
        }

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElse(null);
    }

    @Override
    @Transactional
    public User save(User user) {
        // Ensure role has ROLE_ prefix
        if (user.getRole() != null && !user.getRole().startsWith("ROLE_")) {
            user.setRole("ROLE_" + user.getRole().toUpperCase());
        }
        
        // Add the role to authorities
        if (user.getRole() != null) {
            user.addAuthority(user.getRole());
        }
        
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findRecentUsers(int limit) {
        return userRepository.findTop5ByOrderByCreatedAtDesc();
    }

    @Override
    public long count() {
        return userRepository.count();
    }
} 