package vn.edu.volunteer.controller;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.volunteer.model.*;
import vn.edu.volunteer.service.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manage")
@PreAuthorize("isAuthenticated()")
public class VolunteerController {
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

    @Autowired
    private DanhGiaService danhGiaService;

    @Autowired
    private AuditLogService auditLogService;

    // Dashboard sau khi đăng nhập
    @GetMapping("/home")
    public String dashboard(Model model) {
        model.addAttribute("mostPopularActivity", hoatDongService.getMostPopularActivity());
        return "dashboard";
    }

    // Quản lý sinh viên
    @GetMapping("/sinhvien")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String listSinhVien(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "") String maTruong,
                               Model model) {
        List<SinhVien> sinhViens = sinhVienService.search(keyword, maTruong, page, 10);
        model.addAttribute("sinhViens", sinhViens);
        model.addAttribute("keyword", keyword);
        model.addAttribute("maTruong", maTruong);
        model.addAttribute("truongs", truongService.findAllWithPaging(0, Integer.MAX_VALUE));
        return "sinhvien/list";
    }

    @GetMapping("/sinhvien/new")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String showSinhVienForm(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        model.addAttribute("truongs", truongService.findAllWithPaging(0, Integer.MAX_VALUE));
        return "sinhvien/form";
    }

    @PostMapping("/sinhvien/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String saveSinhVien(@ModelAttribute SinhVien sinhVien, Authentication auth) {
        sinhVienService.save(sinhVien);
        auditLogService.log(auth.getName(), "SAVE_SINHVIEN", "Saved sinh vien: " + sinhVien.getMssv());
        return "redirect:/manage/sinhvien?successMessage=Thêm sinh viên thành công";
    }

    @GetMapping("/sinhvien/{mssv}/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String showEditSinhVienForm(@PathVariable String mssv, Model model) {
        SinhVien sinhVien = sinhVienService.findById(mssv);
        model.addAttribute("sinhVien", sinhVien);
        model.addAttribute("truongs", truongService.findAllWithPaging(0, Integer.MAX_VALUE));
        model.addAttribute("totalHours", sinhVienService.getTotalVolunteerHours(mssv));
        return "sinhvien/form";
    }

    @GetMapping("/sinhvien/{mssv}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteSinhVien(@PathVariable String mssv, Authentication auth) {
        sinhVienService.deleteById(mssv);
        auditLogService.log(auth.getName(), "DELETE_SINHVIEN", "Deleted sinh vien: " + mssv);
        return "redirect:/manage/sinhvien?successMessage=Xóa sinh viên thành công";
    }

    @GetMapping("/sinhvien/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String listAllSinhVien(Model model) {
        List<SinhVien> sinhViens = sinhVienService.findAll();
        model.addAttribute("sinhViens", sinhViens);
        return "sinhvien/list";
    }

    // Quản lý hoạt động
    @GetMapping("/hoatdong")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String listHoatDong(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "") String maToChuc,
                               Model model) {
        List<HoatDong> hoatDongs = hoatDongService.search(keyword, maToChuc, page, 10);
        model.addAttribute("hoatDongs", hoatDongs);
        model.addAttribute("keyword", keyword);
        model.addAttribute("maToChuc", maToChuc);
        model.addAttribute("toChucs", toChucService.findAllWithPaging(0, Integer.MAX_VALUE));
        return "hoatdong/list";
    }

    @GetMapping("/hoatdong/new")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String showHoatDongForm(Model model) {
        model.addAttribute("hoatDong", new HoatDong());
        model.addAttribute("toChucs", toChucService.findAllWithPaging(0, Integer.MAX_VALUE));
        return "hoatdong/form";
    }

    @PostMapping("/hoatdong/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String saveHoatDong(@ModelAttribute HoatDong hoatDong, Authentication auth) {
        hoatDongService.save(hoatDong);
        auditLogService.log(auth.getName(), "SAVE_HOATDONG", "Saved hoat dong: " + hoatDong.getMaHD());
        return "redirect:/manage/hoatdong?successMessage=Thêm hoạt động thành công";
    }

    @GetMapping("/hoatdong/{maHD}/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String showEditHoatDongForm(@PathVariable String maHD, Model model) {
        HoatDong hoatDong = hoatDongService.findById(maHD);
        model.addAttribute("hoatDong", hoatDong);
        model.addAttribute("toChucs", toChucService.findAllWithPaging(0, Integer.MAX_VALUE));
        return "hoatdong/form";
    }

    @GetMapping("/hoatdong/{maHD}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteHoatDong(@PathVariable String maHD, Authentication auth) {
        hoatDongService.deleteById(maHD);
        auditLogService.log(auth.getName(), "DELETE_HOATDONG", "Deleted hoat dong: " + maHD);
        return "redirect:/manage/hoatdong?successMessage=Xóa hoạt động thành công";
    }

    // Quản lý tham gia
    @GetMapping("/thamgia/new")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STUDENT')")
    public String showThamGiaForm(Model model) {
        model.addAttribute("thamGia", new ThamGia());
        model.addAttribute("sinhViens", sinhVienService.findAllWithPaging(0, Integer.MAX_VALUE));
        model.addAttribute("hoatDongs", hoatDongService.findAllWithPaging(0, Integer.MAX_VALUE));
        return "thamgia/form";
    }

    @PostMapping("/thamgia/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STUDENT')")
    public String saveThamGia(@ModelAttribute ThamGia thamGia, Authentication auth) {
        try {
            thamGiaService.save(thamGia);
            auditLogService.log(auth.getName(), "SAVE_THAMGIA", "Saved tham gia: " + thamGia.getMssv() + ", " + thamGia.getMaHD());
            return "redirect:/manage/thamgia?successMessage=Thêm tham gia thành công";
        } catch (IllegalStateException e) {
            return "redirect:/manage/thamgia/new?errorMessage=" + e.getMessage();
        }
    }

    @GetMapping("/thamgia/{mssv}/{maHD}/approve")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String approveThamGia(@PathVariable String mssv, @PathVariable String maHD, Authentication auth) {
        thamGiaService.approveThamGia(mssv, maHD);
        auditLogService.log(auth.getName(), "APPROVE_THAMGIA", "Approved tham gia: " + mssv + ", " + maHD);
        return "redirect:/manage/thamgia?successMessage=Đã duyệt tham gia thành công";
    }

    @GetMapping("/thamgia/{mssv}/{maHD}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteThamGia(@PathVariable String mssv, @PathVariable String maHD, Authentication auth) {
        thamGiaService.deleteById(mssv, maHD);
        auditLogService.log(auth.getName(), "DELETE_THAMGIA", "Deleted tham gia: " + mssv + ", " + maHD);
        return "redirect:/manage/thamgia?successMessage=Xóa tham gia thành công";
    }

    // Báo cáo và thống kê
    @GetMapping("/report/export")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void exportReport(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=report.xlsx");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Báo cáo");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("MSSV");
        header.createCell(1).setCellValue("Họ Tên");
        header.createCell(2).setCellValue("Tổng Giờ");

        List<SinhVien> sinhViens = sinhVienService.findAll();
        int rowNum = 1;
        for (SinhVien sv : sinhViens) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sv.getMssv());
            row.createCell(1).setCellValue(sv.getHoTen());
            row.createCell(2).setCellValue(sinhVienService.getTotalVolunteerHours(sv.getMssv()));
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // Quản lý đánh giá
    @GetMapping("/danhgia/new")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STUDENT')")
    public String showDanhGiaForm(@RequestParam("mssv") String mssv, @RequestParam("maHD") String maHD, Model model) {
        DanhGia danhGia = danhGiaService.findById(mssv, maHD);
        if (danhGia == null) {
            danhGia = new DanhGia();
            danhGia.setMssv(mssv);
            danhGia.setMaHD(maHD);
        }
        model.addAttribute("danhGia", danhGia);
        return "danhgia/form";
    }

    @PostMapping("/danhgia/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STUDENT')")
    public String saveDanhGia(@ModelAttribute DanhGia danhGia, Authentication auth) {
        danhGiaService.save(danhGia);
        auditLogService.log(auth.getName(), "SAVE_DANHGIA", "Saved danh gia: " + danhGia.getMssv() + ", " + danhGia.getMaHD());
        return "redirect:/search/sinhvien?mssv=" + danhGia.getMssv() + "&successMessage=Thêm đánh giá thành công";
    }
}