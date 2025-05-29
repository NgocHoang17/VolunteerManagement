package vn.edu.volunteer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.volunteer.service.*;
import vn.edu.volunteer.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller xử lý các chức năng của quản trị viên
 * Yêu cầu có quyền ROLE_ADMIN để truy cập
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    // Service xử lý thông tin người dùng
    @Autowired
    private UserService userService;
    
    // Service xử lý thông tin sinh viên
    @Autowired
    private SinhVienService sinhVienService;
    
    // Service xử lý thông tin tổ chức
    @Autowired
    private ToChucService toChucService;
    
    // Service xử lý thông tin hoạt động
    @Autowired
    private HoatDongService hoatDongService;

    @Autowired
    private ChungNhanService chungNhanService;

    /**
     * Hiển thị trang dashboard của admin
     * Bao gồm:
     * - Thống kê tổng số sinh viên, tổ chức, hoạt động, chứng nhận
     * - Danh sách hoạt động mới
     * - Danh sách người dùng mới đăng ký
     */
    @GetMapping({"/", "/dashboard"})
    public String showDashboard(Model model, Authentication authentication) {
        logger.info("=== Starting to load admin dashboard ===");
        
        try {
            // Load counts
            model.addAttribute("totalStudents", sinhVienService.count());
            model.addAttribute("totalOrganizations", toChucService.count());
            model.addAttribute("totalActivities", hoatDongService.count());
            model.addAttribute("totalCertificates", chungNhanService.count());
            
            // Load recent data
            model.addAttribute("recentActivities", hoatDongService.findRecentActivities(5));
            model.addAttribute("recentUsers", userService.findRecentUsers(5));
            
            return "admin/dashboard";
            
        } catch (Exception e) {
            logger.error("Error loading admin dashboard: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải dữ liệu");
            model.addAttribute("errorDetails", e.getMessage());
            return "admin/dashboard"; // Return dashboard with error message instead of 500
        }
    }

    /**
     * Hiển thị trang quản lý sinh viên
     */
    @GetMapping("/students")
    public String listStudents(
            @RequestParam(required = false) String maSinhVien,
            @RequestParam(required = false) String hoTen,
            @RequestParam(required = false) String lop,
            @PageableDefault(size = 10) Pageable pageable,
            Model model) {
        
        Page<SinhVien> students = sinhVienService.findAll(maSinhVien, hoTen, lop, pageable);
        model.addAttribute("students", students);
        model.addAttribute("maSinhVien", maSinhVien);
        model.addAttribute("hoTen", hoTen);
        model.addAttribute("lop", lop);
        return "admin/students";
    }

    /**
     * Hiển thị trang quản lý tổ chức
     */
    @GetMapping("/organizations")
    public String showOrganizations(
            @RequestParam(required = false) String maToChuc,
            @RequestParam(required = false) String tenToChuc,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String trangThai,
            @PageableDefault(size = 10) Pageable pageable,
            Model model) {
        try {
            ToChuc.TrangThaiXacThuc trangThaiEnum = null;
            if (trangThai != null && !trangThai.isEmpty()) {
                try {
                    trangThaiEnum = ToChuc.TrangThaiXacThuc.valueOf(trangThai);
                } catch (IllegalArgumentException e) {
                    logger.warn("Invalid trangThai value: {}", trangThai);
                }
            }
            
            Page<ToChuc> organizations = toChucService.findAll(maToChuc, tenToChuc, email, trangThaiEnum, pageable);
            model.addAttribute("organizations", organizations);
            model.addAttribute("trangThaiValues", ToChuc.TrangThaiXacThuc.values());
            return "admin/organizations";
        } catch (Exception e) {
            logger.error("Error loading organizations page: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách tổ chức");
            return "error/500";
        }
    }

    /**
     * Hiển thị trang quản lý hoạt động
     */
    @GetMapping("/activities")
    public String showActivities(
            @RequestParam(required = false) String maHoatDong,
            @RequestParam(required = false) String tenHoatDong,
            @RequestParam(required = false) String maToChuc,
            @RequestParam(required = false) String trangThai,
            @PageableDefault(size = 10) Pageable pageable,
            Model model) {
        try {
            Page<HoatDong> activities = hoatDongService.findAll(maHoatDong, tenHoatDong, maToChuc, trangThai, pageable);
            model.addAttribute("activities", activities);
            // Get all organizations for filter dropdown, using unpaged request
            model.addAttribute("organizations", toChucService.findAll(Pageable.unpaged()));
            return "admin/activities";
        } catch (Exception e) {
            logger.error("Error loading activities page: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách hoạt động");
            return "error/500";
        }
    }

    /**
     * Hiển thị trang quản lý chứng nhận
     */
    @GetMapping("/certificates")
    public String showCertificates(
            @RequestParam(required = false) String maChungNhan,
            @RequestParam(required = false) String tenSinhVien,
            @RequestParam(required = false) String tenHoatDong,
            @RequestParam(required = false) String trangThai,
            @PageableDefault(size = 10) Pageable pageable,
            Model model) {
        try {
            Page<ChungNhan> certificates = chungNhanService.findAll(maChungNhan, tenSinhVien, tenHoatDong, trangThai, pageable);
            model.addAttribute("certificates", certificates);
            return "admin/certificates";
        } catch (Exception e) {
            logger.error("Error loading certificates page: {}", e.getMessage(), e);
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách chứng nhận");
            return "error/500";
        }
    }

    /**
     * Duyệt chứng nhận
     */
    @PostMapping("/certificates/{maChungNhan}/approve")
    public String approveCertificate(
            @PathVariable String maChungNhan,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            User admin = userService.findByUsername(authentication.getName());
            chungNhanService.approve(maChungNhan, admin);
            redirectAttributes.addFlashAttribute("message", "Đã duyệt chứng nhận thành công");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            logger.error("Error approving certificate {}: {}", maChungNhan, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra khi duyệt chứng nhận");
            redirectAttributes.addFlashAttribute("messageType", "danger");
        }
        return "redirect:/admin/certificates";
    }

    /**
     * Từ chối chứng nhận
     */
    @PostMapping("/certificates/{maChungNhan}/reject")
    public String rejectCertificate(
            @PathVariable String maChungNhan,
            @RequestParam String lyDo,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            User admin = userService.findByUsername(authentication.getName());
            chungNhanService.reject(maChungNhan, lyDo, admin);
            redirectAttributes.addFlashAttribute("message", "Đã từ chối chứng nhận thành công");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            logger.error("Error rejecting certificate {}: {}", maChungNhan, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra khi từ chối chứng nhận");
            redirectAttributes.addFlashAttribute("messageType", "danger");
        }
        return "redirect:/admin/certificates";
    }
} 