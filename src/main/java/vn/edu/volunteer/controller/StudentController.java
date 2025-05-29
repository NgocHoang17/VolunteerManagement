package vn.edu.volunteer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.model.DanhGia;
import vn.edu.volunteer.service.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Controller xử lý các chức năng dành cho sinh viên
 * Yêu cầu có quyền ROLE_STUDENT để truy cập
 */
@Controller
@RequestMapping("/student")
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class StudentController {
    
    // Logger để ghi log
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    // Service xử lý thông tin sinh viên
    @Autowired
    private SinhVienService sinhVienService;
    
    // Service xử lý thông tin hoạt động
    @Autowired
    private HoatDongService hoatDongService;
    
    // Service xử lý thông tin tham gia hoạt động
    @Autowired
    private ThamGiaService thamGiaService;
    
    // Service xử lý thông tin đánh giá
    @Autowired
    private DanhGiaService danhGiaService;

    // Service ghi log hệ thống
    @Autowired
    private AuditLogService auditLogService;

    // Service xử lý file
    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Hiển thị trang dashboard của sinh viên
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        try {
            // Lấy thông tin sinh viên
            SinhVien sinhVien = sinhVienService.findByUsername(authentication.getName());
            if (sinhVien == null) {
                logger.error("Không tìm thấy thông tin sinh viên cho user: {}", authentication.getName());
                return "redirect:/error";
            }

            // Thống kê hoạt động
            model.addAttribute("totalActivities", thamGiaService.countByStudent(sinhVien));
            model.addAttribute("totalHours", sinhVien.getTongGioTinhNguyen());
            model.addAttribute("totalPoints", sinhVien.getSoDiemTichLuy());
            model.addAttribute("totalCertificates", sinhVien.getChungNhans() != null ? sinhVien.getChungNhans().size() : 0);

            // Danh sách hoạt động gần đây
            model.addAttribute("recentActivities", thamGiaService.findRecentActivitiesByStudent(sinhVien, 5));

            logger.info("Loaded dashboard for student: {}", sinhVien.getMaSinhVien());
            return "student/dashboard";
            
        } catch (Exception e) {
            logger.error("Error loading student dashboard", e);
            return "redirect:/error";
        }
    }

    /**
     * Hiển thị danh sách hoạt động có thể đăng ký
     * Hỗ trợ tìm kiếm và phân trang
     */
    @GetMapping("/activities")
    public String listActivities(@RequestParam(required = false) String keyword,
                               @RequestParam(defaultValue = "0") int page,
                               Model model) {
        model.addAttribute("activities", hoatDongService.search(keyword, page, 9));
        return "student/activities";
    }

    /**
     * Đăng ký tham gia hoạt động
     */
    @PostMapping("/activities/{maHoatDong}/register")
    public String registerActivity(@PathVariable String maHoatDong, Authentication auth) {
        SinhVien sinhVien = sinhVienService.findByUserId(auth.getName());
        try {
            thamGiaService.register(sinhVien.getMaSinhVien(), maHoatDong);
            auditLogService.log(auth.getName(), "REGISTER_ACTIVITY", 
                "Registered for activity: " + maHoatDong);
            return "redirect:/student/activities?success=true";
        } catch (IllegalStateException e) {
            return "redirect:/student/activities?error=" + e.getMessage();
        }
    }

    /**
     * Hủy đăng ký tham gia hoạt động
     */
    @PostMapping("/activities/{maHoatDong}/cancel")
    public String cancelRegistration(@PathVariable String maHoatDong, Authentication auth) {
        SinhVien sinhVien = sinhVienService.findByUserId(auth.getName());
        try {
            thamGiaService.cancel(sinhVien.getMaSinhVien(), maHoatDong);
            auditLogService.log(auth.getName(), "CANCEL_REGISTRATION", 
                "Cancelled registration for activity: " + maHoatDong);
            return "redirect:/student/activities?success=true";
        } catch (IllegalStateException e) {
            return "redirect:/student/activities?error=" + e.getMessage();
        }
    }

    /**
     * Hiển thị lịch sử tham gia hoạt động của sinh viên
     * Bao gồm thống kê tổng quan và chi tiết từng hoạt động
     */
    @GetMapping("/history")
    public String history(Model model, Authentication auth) {
        SinhVien sinhVien = sinhVienService.findByUserId(auth.getName());
        model.addAttribute("participations", thamGiaService.findBySinhVien(sinhVien.getMaSinhVien()));
        model.addAttribute("totalActivities", thamGiaService.countActivities(sinhVien.getMaSinhVien()));
        model.addAttribute("totalHours", thamGiaService.countHours(sinhVien.getMaSinhVien()));
        model.addAttribute("totalPoints", thamGiaService.countPoints(sinhVien.getMaSinhVien()));
        model.addAttribute("totalCertificates", thamGiaService.countCertificates(sinhVien.getMaSinhVien()));
        return "student/history";
    }

    /**
     * Hiển thị danh sách chứng nhận của sinh viên
     */
    @GetMapping("/certificates")
    public String certificates(Model model, Authentication auth) {
        SinhVien sinhVien = sinhVienService.findByUserId(auth.getName());
        model.addAttribute("certificates", sinhVienService.getCertificates(sinhVien.getMaSinhVien()));
        return "student/certificates";
    }

    /**
     * Hiển thị trang thông tin cá nhân sinh viên
     */
    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        SinhVien sinhVien = sinhVienService.findByUserId(auth.getName());
        model.addAttribute("sinhVien", sinhVien);
        return "student/profile";
    }

    /**
     * Cập nhật thông tin cá nhân sinh viên
     * Hỗ trợ upload ảnh đại diện
     */
    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute SinhVien sinhVien,
                              BindingResult result,
                              @RequestParam(required = false) MultipartFile avatar,
                              Authentication auth) {
        if (result.hasErrors()) {
            return "student/profile";
        }

        if (avatar != null && !avatar.isEmpty()) {
            String avatarUrl = fileStorageService.storeFile(avatar);
            sinhVien.getUser().setAvatarUrl(avatarUrl);
        }

        sinhVienService.update(sinhVien);
        auditLogService.log(auth.getName(), "UPDATE_PROFILE", "Updated student profile");
        return "redirect:/student/profile?success=true";
    }

    /**
     * Hiển thị form đánh giá hoạt động
     * Chỉ cho phép đánh giá hoạt động đã hoàn thành
     */
    @GetMapping("/activities/{maHoatDong}/review")
    public String showReviewForm(@PathVariable String maHoatDong, Model model, Authentication auth) {
        SinhVien sinhVien = sinhVienService.findByUserId(auth.getName());
        ThamGia thamGia = thamGiaService.findById(sinhVien.getMaSinhVien(), maHoatDong);
        
        if (thamGia == null || !thamGia.getTrangThai().equals("COMPLETED")) {
            return "redirect:/student/history?error=Không thể đánh giá hoạt động này";
        }

        DanhGia danhGia = danhGiaService.findById(sinhVien.getMaSinhVien(), maHoatDong);
        if (danhGia == null) {
            danhGia = new DanhGia();
            danhGia.setSinhVien(sinhVien);
            danhGia.setHoatDong(thamGia.getHoatDong());
        }

        model.addAttribute("danhGia", danhGia);
        model.addAttribute("hoatDong", thamGia.getHoatDong());
        return "student/review-form";
    }

    /**
     * Lưu đánh giá hoạt động
     */
    @PostMapping("/activities/{maHoatDong}/review")
    public String submitReview(@PathVariable String maHoatDong,
                             @Valid @ModelAttribute DanhGia danhGia,
                             BindingResult result,
                             Authentication auth) {
        if (result.hasErrors()) {
            return "student/review-form";
        }

        SinhVien sinhVien = sinhVienService.findByUserId(auth.getName());
        danhGia.setSinhVien(sinhVien);
        danhGia.setHoatDong(hoatDongService.findById(maHoatDong));

        danhGiaService.save(danhGia);
        auditLogService.log(auth.getName(), "SUBMIT_REVIEW", 
            "Submitted review for activity: " + maHoatDong);
        return "redirect:/student/history?success=true";
    }

    /**
     * Tải chứng nhận hoàn thành hoạt động
     */
    @GetMapping("/certificates/{maCN}/download")
    public void downloadCertificate(@PathVariable String maCN,
                                  HttpServletResponse response,
                                  Authentication auth) {
        SinhVien sinhVien = sinhVienService.findByUserId(auth.getName());
        sinhVienService.downloadCertificate(sinhVien.getMaSinhVien(), maCN, response);
    }
} 