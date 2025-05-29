package vn.edu.volunteer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository để quản lý thông tin người dùng trong hệ thống
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Tìm người dùng theo tên đăng nhập
    Optional<User> findByUsername(String username);
    
    // Tìm người dùng theo email
    Optional<User> findByEmail(String email);
    
    // Kiểm tra tên đăng nhập đã tồn tại chưa
    boolean existsByUsername(String username);
    
    // Kiểm tra email đã tồn tại chưa
    boolean existsByEmail(String email);
    
    // Xóa người dùng theo tên đăng nhập
    @Modifying
    @Query("DELETE FROM User u WHERE u.username = ?1")
    void deleteByUsername(String username);

    // Lấy danh sách người dùng mới nhất với số lượng giới hạn
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.authorities LEFT JOIN FETCH u.sinhVien LEFT JOIN FETCH u.toChuc ORDER BY u.createdAt DESC")
    List<User> findRecentUsers(int limit);

    // Lấy 5 người dùng mới nhất trong hệ thống
    List<User> findTop5ByOrderByCreatedAtDesc();
}