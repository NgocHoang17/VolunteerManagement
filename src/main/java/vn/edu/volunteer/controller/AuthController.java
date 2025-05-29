package vn.edu.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.volunteer.model.User;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.service.UserService;
import vn.edu.volunteer.service.SinhVienService;

import javax.validation.Valid;

/**
 * Controller xử lý các chức năng liên quan đến xác thực người dùng
 * Bao gồm: đăng nhập, đăng ký, quên mật khẩu, đặt lại mật khẩu
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    // Service xử lý logic người dùng
    @Autowired
    private UserService userService;

    // Service xử lý logic sinh viên
    @Autowired
    private SinhVienService sinhVienService;

    // Encoder để mã hóa mật khẩu
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Hiển thị form đăng nhập
     * Nếu đã đăng nhập thì chuyển về trang chủ
     */
    @GetMapping("/login")
    public String showLoginForm(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/home";
        }
        return "auth/login";
    }

    /**
     * Chuyển hướng /auth về trang đăng nhập
     */
    @GetMapping("")
    public String redirectToLogin() {
        return "redirect:/auth/login";
    }

    /**
     * Hiển thị form đăng ký tài khoản mới
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            User user = new User();
            user.setEnabled(true);
            user.setBlocked(false);
            model.addAttribute("user", user);
        }
        return "auth/register";
    }

    /**
     * Xử lý đăng ký tài khoản mới
     * - Kiểm tra dữ liệu hợp lệ
     * - Kiểm tra username và email đã tồn tại chưa
     * - Tạo tài khoản người dùng và sinh viên
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra lỗi validation
            if (result.hasErrors()) {
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
                redirectAttributes.addFlashAttribute("user", user);
                return "redirect:/auth/register";
            }

            // Kiểm tra username đã tồn tại
            if (userService.existsByUsername(user.getUsername())) {
                result.rejectValue("username", "error.username", "Tên đăng nhập đã tồn tại");
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
                redirectAttributes.addFlashAttribute("user", user);
                return "redirect:/auth/register";
            }

            // Kiểm tra email đã tồn tại
            if (userService.existsByEmail(user.getEmail())) {
                result.rejectValue("email", "error.email", "Email đã được sử dụng");
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
                redirectAttributes.addFlashAttribute("user", user);
                return "redirect:/auth/register";
            }

            // Thiết lập thông tin mặc định cho user
            user.setRole("STUDENT"); // UserService sẽ thêm tiền tố ROLE_
            user.setEnabled(true);
            user.setBlocked(false);
            
            // Mã hóa mật khẩu
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            
            // Lưu thông tin user
            User savedUser = userService.save(user);
            
            // Tạo và lưu thông tin sinh viên
            SinhVien sinhVien = new SinhVien();
            sinhVien.setUser(savedUser);
            sinhVien.setHoTen(savedUser.getFullName());
            sinhVien.setEmail(savedUser.getEmail());
            sinhVien.setSoDienThoai(savedUser.getPhone());
            
            // Thiết lập các trường mặc định cho sinh viên
            sinhVien.setMaSinhVien("SV" + String.format("%06d", savedUser.getId())); // Tạo mã sinh viên
            sinhVien.setTruong("Chưa cập nhật");
            sinhVien.setTongGioTinhNguyen(0);
            sinhVien.setSoDiemTichLuy(0);
            sinhVien.setSoGioThamGia(0);
            
            sinhVienService.save(sinhVien);
            
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/auth/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/auth/register";
        }
    }

    /**
     * Hiển thị trang thông báo không có quyền truy cập
     */
    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "auth/access-denied";
    }

    /**
     * Hiển thị form quên mật khẩu
     */
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot-password";
    }

    /**
     * Hiển thị form đặt lại mật khẩu
     * Yêu cầu có token hợp lệ
     */
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam(required = false) String token, Model model) {
        if (token == null || token.isEmpty()) {
            return "redirect:/auth/forgot-password";
        }
        model.addAttribute("token", token);
        return "auth/reset-password";
    }
} 