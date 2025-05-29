package vn.edu.volunteer.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

/**
 * Controller xử lý các trang chính của hệ thống
 * Bao gồm: trang chủ, xử lý lỗi và phân quyền truy cập
 */
@Controller
public class HomeController {

    /**
     * Xử lý trang chủ và điều hướng theo vai trò người dùng
     * - ADMIN -> /admin/dashboard
     * - ORGANIZATION -> /organization/dashboard
     * - STUDENT -> /student/dashboard
     * - Chưa đăng nhập -> trang chủ mặc định
     */
    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            
            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/admin/dashboard";
            } else if (roles.contains("ROLE_ORGANIZATION")) {
                return "redirect:/organization/dashboard";
            } else if (roles.contains("ROLE_STUDENT")) {
                return "redirect:/student/dashboard";
            }
        }
        return "public/home";
    }

    @GetMapping("/home")
    public String home(Authentication authentication) {
        return root(authentication);
    }

    /**
     * Xử lý hiển thị trang lỗi chung
     */
    @GetMapping("/error")
    public String handleError() {
        return "error/error";
    }

    /**
     * Xử lý hiển thị trang không có quyền truy cập (403)
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/403";
    }
} 