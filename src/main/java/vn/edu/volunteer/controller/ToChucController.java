package vn.edu.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.volunteer.model.ToChuc;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.service.ToChucService;
import vn.edu.volunteer.service.AuditLogService;
import vn.edu.volunteer.service.FileStorageService;
import vn.edu.volunteer.service.HoatDongService;
import vn.edu.volunteer.service.ThamGiaService;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller xử lý các chức năng dành cho tổ chức
 * Yêu cầu có quyền ROLE_ORGANIZATION để truy cập
 */
@Controller
@RequestMapping("/organization")
@PreAuthorize("hasRole('ROLE_ORGANIZATION')")
public class ToChucController {
    // Service xử lý thông tin tổ chức
    @Autowired
    private ToChucService toChucService;

    // Service xử lý thông tin hoạt động
    @Autowired
    private HoatDongService hoatDongService;

    // Service ghi log hệ thống
    @Autowired
    private AuditLogService auditLogService;

    // Service xử lý file
    @Autowired
    private FileStorageService fileStorageService;
    
    // Service xử lý thông tin tham gia
    @Autowired
    private ThamGiaService thamGiaService;

    /**
     * Hiển thị trang dashboard của tổ chức
     * Bao gồm:
     * - Thông tin tổ chức
     * - Thống kê số hoạt động, số tình nguyện viên
     * - Danh sách tình nguyện viên và hoạt động gần đây
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        try {
            ToChuc toChuc = toChucService.findByUserId(auth.getName());
            model.addAttribute("toChuc", toChuc);
            model.addAttribute("totalActivities", hoatDongService.countByToChuc(toChuc));
            model.addAttribute("activeActivities", hoatDongService.countActiveByToChuc(toChuc));
            model.addAttribute("totalVolunteers", thamGiaService.countVolunteersByToChuc(toChuc));
            model.addAttribute("totalCertificates", hoatDongService.countCertificatesByToChuc(toChuc));
            model.addAttribute("recentActivities", hoatDongService.findRecentActivitiesByToChuc(toChuc, 5));
            return "organization/dashboard";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải dữ liệu");
            return "organization/dashboard";
        }
    }

    /**
     * Hiển thị trang thông tin tổ chức
     */
    @GetMapping("/profile")
    public String showProfile(Model model, Authentication auth) {
        try {
            ToChuc toChuc = toChucService.findByUserId(auth.getName());
            model.addAttribute("toChuc", toChuc);
            return "organization/profile";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải thông tin tổ chức");
            return "organization/profile";
        }
    }

    /**
     * Cập nhật thông tin tổ chức
     */
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute @Valid ToChuc toChuc, BindingResult result, 
                              Authentication auth, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "organization/profile";
        }

        try {
            ToChuc existingToChuc = toChucService.findByUserId(auth.getName());
            toChuc.setMaToChuc(existingToChuc.getMaToChuc());
            toChuc.setUser(existingToChuc.getUser());
            toChucService.save(toChuc);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin thành công");
            return "redirect:/organization/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật thông tin");
            return "redirect:/organization/profile";
        }
    }

    /**
     * Hiển thị danh sách hoạt động của tổ chức
     * Có phân trang, mỗi trang 10 hoạt động
     */
    @GetMapping("/activities")
    public String listActivities(@RequestParam(defaultValue = "0") int page, Model model, Authentication auth) {
        try {
            ToChuc toChuc = toChucService.findByUserId(auth.getName());
            model.addAttribute("activities", hoatDongService.findByToChuc(toChuc.getMaToChuc(), page, 10));
            return "organization/activities";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách hoạt động");
            return "organization/activities";
        }
    }

    /**
     * Hiển thị form tạo hoạt động mới
     */
    @GetMapping("/activities/new")
    public String showNewActivityForm(Model model) {
        model.addAttribute("hoatDong", new HoatDong());
        return "organization/activity-form";
    }

    /**
     * Lưu thông tin hoạt động mới hoặc cập nhật
     */
    @PostMapping("/activities/new")
    public String createActivity(@ModelAttribute @Valid HoatDong hoatDong, BindingResult result, 
                               Authentication auth, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "organization/activity-form";
        }

        try {
            ToChuc toChuc = toChucService.findByUserId(auth.getName());
            hoatDong.setToChuc(toChuc);
            hoatDongService.save(hoatDong);
            redirectAttributes.addFlashAttribute("successMessage", "Tạo hoạt động thành công");
            return "redirect:/organization/activities";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tạo hoạt động");
            return "redirect:/organization/activities/new";
        }
    }

    /**
     * Hiển thị danh sách tình nguyện viên đăng ký tham gia hoạt động
     */
    @GetMapping("/activities/{maHoatDong}/volunteers")
    public String activityVolunteers(@PathVariable String maHoatDong, Model model, Authentication auth) {
        ToChuc toChuc = toChucService.findByUserId(auth.getName());
        model.addAttribute("volunteers", toChucService.getActivityVolunteers(toChuc.getMaToChuc(), maHoatDong));
        return "organization/activity-volunteers";
    }

    /**
     * Phê duyệt sinh viên tham gia hoạt động
     */
    @PostMapping("/activities/{maHoatDong}/approve/{maSinhVien}")
    public String approveVolunteer(@PathVariable String maHoatDong,
                                 @PathVariable String maSinhVien,
                                 Authentication auth) {
        toChucService.approveVolunteer(maHoatDong, maSinhVien);
        auditLogService.log(auth.getName(), "APPROVE_VOLUNTEER", 
            "Approved volunteer " + maSinhVien + " for activity " + maHoatDong);
        return "redirect:/organization/activities/" + maHoatDong + "/volunteers?successMessage=Phê duyệt thành công";
    }

    /**
     * Từ chối sinh viên tham gia hoạt động
     */
    @PostMapping("/activities/{maHoatDong}/reject/{maSinhVien}")
    public String rejectVolunteer(@PathVariable String maHoatDong,
                                @PathVariable String maSinhVien,
                                Authentication auth) {
        toChucService.rejectVolunteer(maHoatDong, maSinhVien);
        auditLogService.log(auth.getName(), "REJECT_VOLUNTEER", 
            "Rejected volunteer " + maSinhVien + " for activity " + maHoatDong);
        return "redirect:/organization/activities/" + maHoatDong + "/volunteers?successMessage=Từ chối thành công";
    }

    /**
     * Hiển thị danh sách tất cả tình nguyện viên đã tham gia hoạt động của tổ chức
     */
    @GetMapping("/volunteers")
    public String listVolunteers(@RequestParam(defaultValue = "0") int page, Model model, Authentication auth) {
        try {
            ToChuc toChuc = toChucService.findByUserId(auth.getName());
            model.addAttribute("volunteers", thamGiaService.findVolunteersByToChuc(toChuc, page, 10));
            return "organization/volunteers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách tình nguyện viên");
            return "organization/volunteers";
        }
    }

    /**
     * Xem lịch sử tham gia hoạt động của một sinh viên
     */
    @GetMapping("/volunteers/{maSV}/history")
    public String volunteerHistory(@PathVariable String maSV, Model model, Authentication auth) {
        ToChuc toChuc = toChucService.findByUserId(auth.getName());
        model.addAttribute("history", toChucService.getVolunteerHistory(toChuc.getMaToChuc(), maSV));
        return "organization/volunteer-history";
    }

    /**
     * Chặn sinh viên tham gia hoạt động của tổ chức
     */
    @PostMapping("/volunteers/{maSV}/block")
    public String blockVolunteer(@PathVariable String maSV, Authentication auth) {
        ToChuc toChuc = toChucService.findByUserId(auth.getName());
        toChucService.blockVolunteer(toChuc.getMaToChuc(), maSV);
        auditLogService.log(auth.getName(), "BLOCK_VOLUNTEER", "Blocked volunteer: " + maSV);
        return "redirect:/organization/volunteers?success=true";
    }

    /**
     * Bỏ chặn sinh viên
     */
    @PostMapping("/volunteers/{maSV}/unblock")
    public String unblockVolunteer(@PathVariable String maSV, Authentication auth) {
        ToChuc toChuc = toChucService.findByUserId(auth.getName());
        toChucService.unblockVolunteer(toChuc.getMaToChuc(), maSV);
        auditLogService.log(auth.getName(), "UNBLOCK_VOLUNTEER", "Unblocked volunteer: " + maSV);
        return "redirect:/organization/volunteers?success=true";
    }

    @GetMapping("/certificates")
    public String listCertificates(@RequestParam(defaultValue = "0") int page, Model model, Authentication auth) {
        try {
            ToChuc toChuc = toChucService.findByUserId(auth.getName());
            model.addAttribute("certificates", hoatDongService.findCertificatesByToChuc(toChuc, page, 10));
            return "organization/certificates";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách chứng nhận");
            return "organization/certificates";
        }
    }
} 