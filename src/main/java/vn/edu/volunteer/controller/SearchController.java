package vn.edu.volunteer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.service.*;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private HoatDongService hoatDongService;

    @Autowired
    private ThamGiaService thamGiaService;

    @Autowired
    private TruongService truongService;

    @Autowired
    private ToChucService toChucService;

    @GetMapping
    public String showSearchPage(Model model) {
        model.addAttribute("truongs", truongService.findAllWithPaging(0, Integer.MAX_VALUE));
        model.addAttribute("toChucs", toChucService.findAllWithPaging(0, Integer.MAX_VALUE));
        return "search";
    }

    @GetMapping("/sinhvien")
    public String searchSinhVien(
            @RequestParam(required = false) String mssv,
            @RequestParam(required = false) String hoTen,
            @RequestParam(required = false) String maTruong,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        if (mssv != null && !mssv.isEmpty()) {
            // Tìm kiếm chi tiết sinh viên
            SinhVien sinhVien = sinhVienService.findById(mssv);
            int totalHours = sinhVienService.getTotalVolunteerHours(mssv);
            model.addAttribute("sinhVien", sinhVien);
            model.addAttribute("totalHours", totalHours);
            return "search-sinhvien";
        } else {
            // Tìm kiếm danh sách sinh viên
            List<SinhVien> sinhViens = sinhVienService.searchAdvanced(mssv, hoTen, maTruong, page, 10);
            model.addAttribute("sinhViens", sinhViens);
            model.addAttribute("truongs", truongService.findAllWithPaging(0, Integer.MAX_VALUE));
            return "search";
        }
    }

    @GetMapping("/hoatdong")
    public String searchHoatDong(
            @RequestParam(required = false) String maHD,
            @RequestParam(required = false) String tenHD,
            @RequestParam(required = false) String maToChuc,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        if (maHD != null && !maHD.isEmpty()) {
            // Tìm kiếm chi tiết hoạt động
            HoatDong hoatDong = hoatDongService.findById(maHD);
            List<ThamGia> thamGias = thamGiaService.findByHoatDong(maHD);
            model.addAttribute("hoatDong", hoatDong);
            model.addAttribute("thamGias", thamGias);
            return "search-hoatdong";
        } else {
            // Tìm kiếm danh sách hoạt động
            List<HoatDong> hoatDongs = hoatDongService.searchAdvanced(maHD, tenHD, maToChuc, page, 10);
            model.addAttribute("hoatDongs", hoatDongs);
            model.addAttribute("toChucs", toChucService.findAllWithPaging(0, Integer.MAX_VALUE));
            return "search";
        }
    }

    @GetMapping("/thamgia")
    public String searchThamGia(
            @RequestParam(required = false) String mssv,
            @RequestParam(required = false) String maHD,
            @RequestParam(required = false) String trangThai,
            @RequestParam(required = false) String xepLoai,
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        
        List<ThamGia> thamGias = thamGiaService.searchAdvanced(mssv, maHD, trangThai, xepLoai, page, 10);
        model.addAttribute("thamGias", thamGias);
        model.addAttribute("sinhViens", sinhVienService.findAllWithPaging(0, Integer.MAX_VALUE));
        model.addAttribute("hoatDongs", hoatDongService.findAllWithPaging(0, Integer.MAX_VALUE));
        return "search";
    }
} 