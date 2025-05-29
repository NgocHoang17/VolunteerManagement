package vn.edu.volunteer.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entity đại diện cho người dùng trong hệ thống
 * Implements UserDetails để tích hợp với Spring Security
 */
@Getter
@Setter
@ToString(exclude = {"authorities", "sinhVien", "toChuc"})
@EqualsAndHashCode(exclude = {"authorities", "sinhVien", "toChuc"})
@Entity
@Table(name = "users")
public class User implements UserDetails {
    //  id tự động tăng
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    

     // Tên đăng nhập, độ dài 3-50 ký tự, không được trùng
    @NotBlank(message = "Tên đăng nhập không được để trống")
    @Size(min = 3, max = 50, message = "Tên đăng nhập phải từ 3-50 ký tự")
    @Column(name = "username", unique = true)
    private String username;
    

     // Mật khẩu đã được mã hóa, tối thiểu 6 ký tự
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    @Column(name = "password")
    private String password;
    

     //Email của người dùng, phải hợp lệ và không được trùng
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Column(name = "email", unique = true)
    private String email;
    
    //  họ và tên
    @NotBlank(message = "Họ và tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ và tên phải từ 2-100 ký tự")
    @Column(name = "full_name")
    private String fullName;
    
    // sđt 10 số
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải có 10 chữ số")
    @Column(name = "phone")
    private String phone;
    
    /**
     * Vai trò chính của người dùng (ADMIN, STUDENT, ORGANIZATION)
     */
    @Column(name = "role")
    private String role;
    
    /**
     * Danh sách các quyền của người dùng
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private Set<String> authorities = new HashSet<>();
    
    // trạng thái kích hoạt tk
    @Column(name = "enabled")
    private boolean enabled = true;
    
    // trạng thái khóa tk
    @Column(name = "blocked")
    private boolean blocked = false;
    
    // url ảnh đại diện
    @Column(name = "avatar_url")
    private String avatarUrl;
    
   // thời điểm tạo tk
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    // thời điểm cập nhật gần nhất
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // thông tin sinh viên nếu role là sinh vien
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private SinhVien sinhVien;
    
    // thông tin tổ chức if role tổ chức
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ToChuc toChuc;


     // Lấy danh sách quyền cho Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (this.role != null) {
            authorities.add(new SimpleGrantedAuthority(
                this.role.startsWith("ROLE_") ? this.role : "ROLE_" + this.role
            ));
        }
        if (this.authorities != null) {
            this.authorities.forEach(auth -> 
                authorities.add(new SimpleGrantedAuthority(auth))
            );
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !blocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // thêm quyền mới cho người dùng
    public void addAuthority(String authority) {
        if (this.authorities == null) {
            this.authorities = new HashSet<>();
        }
        if (!authority.startsWith("ROLE_")) {
            authority = "ROLE_" + authority;
        }
        this.authorities.add(authority);
    }

    // xử lý trước khi lưu mới
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        if (role != null) {
            String roleToAdd = role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase();
            role = roleToAdd;
            authorities.add(roleToAdd);
        }
    }

    // xử  lý trước khi cập nhật
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (role != null) {
            String roleToAdd = role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase();
            role = roleToAdd;
            if (authorities != null) {
                authorities.add(roleToAdd);
            }
        }
    }

    // Các phương thức getter và setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}