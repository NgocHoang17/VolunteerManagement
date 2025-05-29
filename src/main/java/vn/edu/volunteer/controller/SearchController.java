package vn.edu.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.model.ToChuc;
import vn.edu.volunteer.service.*;

import java.util.List;

/**
 * Controller xử lý các chức năng tìm kiếm trong hệ thống
 * Bao gồm: tìm kiếm sinh viên, hoạt động, đăng ký tham gia
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    // Service xử lý thông tin sinh viên
    @Autowired
    private SinhVienService sinhVienService;

    // Service xử lý thông tin hoạt động
    @Autowired
    private HoatDongService hoatDongService;

    // Service xử lý thông tin tham gia
    @Autowired
    private ThamGiaService thamGiaService;

    // Service xử lý thông tin tổ chức
    @Autowired
    private ToChucService toChucService;

    /**
     * Hiển thị trang tìm kiếm chính
     * Kèm danh sách tổ chức để lọc
     */
    @GetMapping
    public String showSearchPage(Model model) {
        model.addAttribute("toChucs", toChucService.findAllWithPaging(0, Integer.MAX_VALUE));
        return "search";
    }

    /**
     * Tìm kiếm sinh viên
     * - Nếu có mã sinh viên cụ thể: hiển thị chi tiết sinh viên và số giờ tham gia
     * - Nếu không: hiển thị danh sách sinh viên theo điều kiện tìm kiếm
     */
    @GetMapping("/students")
    public String searchStudents(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        try {
            List<SinhVien> students = sinhVienService.search(keyword, page, size);
            model.addAttribute("students", students);
            model.addAttribute("keyword", keyword);
            return "search/students";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi tìm kiếm sinh viên");
            return "error";
        }
    }

    /**
     * Tìm kiếm hoạt động
     * - Nếu có mã hoạt động cụ thể: hiển thị chi tiết hoạt động và danh sách đăng ký
     * - Nếu không: hiển thị danh sách hoạt động theo điều kiện tìm kiếm
     */
    @GetMapping("/activities")
    public String searchActivities(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        try {
            List<HoatDong> activities = hoatDongService.search(keyword, page, size);
            model.addAttribute("activities", activities);
            model.addAttribute("keyword", keyword);
            return "search/activities";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi tìm kiếm hoạt động");
            return "error";
        }
    }

    /**
     * Tìm kiếm đăng ký tham gia hoạt động
     * Hỗ trợ lọc theo:
     * - Mã sinh viên
     * - Mã hoạt động
     * - Trạng thái đăng ký
     * - Xếp loại kết quả
     */
    @GetMapping("/registrations")
    public String searchRegistrations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String maSinhVien,
            @RequestParam(required = false) String maHoatDong,
            @RequestParam(required = false) String trangThai,
            @RequestParam(required = false) String xepLoai,
            Model model) {

        List<ThamGia> thamGias = thamGiaService.searchAdvanced(maSinhVien, maHoatDong, trangThai, xepLoai, page, 10);
        model.addAttribute("thamGias", thamGias);
        model.addAttribute("currentPage", page);
        return "search/registrations";
    }

    @GetMapping("/students/{maSinhVien}")
    public String viewStudent(@PathVariable String maSinhVien, Model model) {
        try {
            SinhVien student = sinhVienService.findById(maSinhVien);
            if (student == null) {
                return "error/404";
            }
            model.addAttribute("student", student);
            return "search/student-detail";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi xem thông tin sinh viên");
            return "error";
        }
    }

    @GetMapping("/organizations")
    public String searchOrganizations(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PageableDefault(size = 10) Pageable pageable,
            Model model) {
        try {
            model.addAttribute("organizations", toChucService.findAll(pageable));
            model.addAttribute("keyword", keyword);
            return "search/organizations";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi tìm kiếm tổ chức");
            return "error";
        }
    }

    @GetMapping("/organizations/{maToChuc}")
    public String viewOrganization(@PathVariable String maToChuc, Model model) {
        try {
            ToChuc organization = toChucService.findByMaToChuc(maToChuc);
            if (organization == null) {
                return "error/404";
            }
            model.addAttribute("organization", organization);
            return "search/organization-detail";
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra khi xem thông tin tổ chức");
            return "error";
        }
    }
} 