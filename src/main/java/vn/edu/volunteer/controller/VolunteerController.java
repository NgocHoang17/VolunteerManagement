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


    // Dashboard
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("mostPopularActivity", hoatDongService.getMostPopularActivity());
        return "navbar";
    }

    // Danh sách sinh viên
    @GetMapping("/sinhvien")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String listSinhVien(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "") String maTruong,
                               Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<SinhVien> sinhViens = sinhVienService.search(keyword, maTruong, pageable);
        model.addAttribute("sinhViens", sinhViens);
        model.addAttribute("keyword", keyword);
        model.addAttribute("maTruong", maTruong);
        model.addAttribute("truongs", truongService.findAll(Pageable.unpaged()).getContent());
        return "sinhvien-list";
    }

    // Form thêm sinh viên
    @GetMapping("/sinhvien/new")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String showSinhVienForm(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        model.addAttribute("truongs", truongService.findAll(Pageable.unpaged()).getContent());
        return "sinhvien-form";
    }

    @PostMapping("/sinhvien/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String saveSinhVien(@ModelAttribute SinhVien sinhVien, Authentication auth) {
        sinhVienService.save(sinhVien);
        auditLogService.log(auth.getName(), "SAVE_SINHVIEN", "Saved sinh vien: " + sinhVien.getMssv());
        return "redirect:/sinhvien?successMessage=Thêm sinh viên thành công";
    }

    // Chỉnh sửa sinh viên
    @GetMapping("/sinhvien/edit/{mssv}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String showEditSinhVienForm(@PathVariable String mssv, Model model) {
        SinhVien sinhVien = sinhVienService.findById(mssv);
        model.addAttribute("sinhVien", sinhVien);
        model.addAttribute("truongs", truongService.findAll(Pageable.unpaged()).getContent());
        return "sinhvien-form";
    }

    // Xóa sinh viên
    @GetMapping("/sinhvien/delete/{mssv}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteSinhVien(@PathVariable String mssv, Authentication auth) {
        sinhVienService.deleteById(mssv);
        auditLogService.log(auth.getName(), "DELETE_SINHVIEN", "Deleted sinh vien: " + mssv);
        return "redirect:/sinhvien?successMessage=Xóa sinh viên thành công";
    }

    // Danh sách hoạt động
    @GetMapping("/hoatdong")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String listHoatDong(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "") String keyword,
                               @RequestParam(defaultValue = "") String maToChuc,
                               Model model) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<HoatDong> hoatDongs = hoatDongService.search(keyword, maToChuc, pageable);
        model.addAttribute("hoatDongs", hoatDongs);
        model.addAttribute("keyword", keyword);
        model.addAttribute("maToChuc", maToChuc);
        model.addAttribute("toChucs", toChucService.findAll(Pageable.unpaged()).getContent());
        return "hoatdong-list";
    }

    // Form thêm hoạt động
    @GetMapping("/hoatdong/new")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String showHoatDongForm(Model model) {
        model.addAttribute("hoatDong", new HoatDong());
        model.addAttribute("toChucs", toChucService.findAll(Pageable.unpaged()).getContent());
        return "hoatdong-form";
    }

    @PostMapping("/hoatdong/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String saveHoatDong(@ModelAttribute HoatDong hoatDong, Authentication auth) {
        hoatDongService.save(hoatDong);
        auditLogService.log(auth.getName(), "SAVE_HOATDONG", "Saved hoat dong: " + hoatDong.getMaHD());
        return "redirect:/hoatdong?successMessage=Thêm hoạt động thành công";
    }

    // Chỉnh sửa hoạt động
    @GetMapping("/hoatdong/edit/{maHD}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String showEditHoatDongForm(@PathVariable String maHD, Model model) {
        HoatDong hoatDong = hoatDongService.findById(maHD);
        model.addAttribute("hoatDong", hoatDong);
        model.addAttribute("toChucs", toChucService.findAll(Pageable.unpaged()).getContent());
        return "hoatdong-form";
    }

    // Xóa hoạt động
    @GetMapping("/hoatdong/delete/{maHD}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteHoatDong(@PathVariable String maHD, Authentication auth) {
        hoatDongService.deleteById(maHD);
        auditLogService.log(auth.getName(), "DELETE_HOATDONG", "Deleted hoat dong: " + maHD);
        return "redirect:/hoatdong?successMessage=Xóa hoạt động thành công";
    }

    // Form thêm tham gia
    @GetMapping("/thamgia/new")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STUDENT')")
    public String showThamGiaForm(Model model) {
        model.addAttribute("thamGia", new ThamGia());
        model.addAttribute("sinhViens", sinhVienService.findAll(Pageable.unpaged()).getContent());
        model.addAttribute("hoatDongs", hoatDongService.findAll(Pageable.unpaged()).getContent());
        return "thamgia-form";
    }

    @PostMapping("/thamgia/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STUDENT')")
    public String saveThamGia(@ModelAttribute ThamGia thamGia, Authentication auth) {
        try {
            thamGiaService.save(thamGia);
            auditLogService.log(auth.getName(), "SAVE_THAMGIA", "Saved tham gia: " + thamGia.getMssv() + ", " + thamGia.getMaHD());
            return "redirect:/thamgia?successMessage=Thêm tham gia thành công";
        } catch (IllegalStateException e) {
            return "redirect:/thamgia/new?errorMessage=" + e.getMessage();
        }
    }

    // Duyệt tham gia
    @GetMapping("/thamgia/approve/{mssv}/{maHD}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String approveThamGia(@PathVariable String mssv, @PathVariable String maHD, Authentication auth) {
        thamGiaService.approveThamGia(mssv, maHD);
        auditLogService.log(auth.getName(), "APPROVE_THAMGIA", "Approved tham gia: " + mssv + ", " + maHD);
        return "redirect:/thamgia?successMessage=Duyệt tham gia thành công";
    }

    // Tìm kiếm sinh viên
    @GetMapping("/search/sinhvien")
    public String searchSinhVien(@RequestParam("mssv") String mssv, Model model) {
        SinhVien sinhVien = sinhVienService.findById(mssv);
        List<ThamGia> thamGias = thamGiaService.findBySinhVien(mssv);
        int totalHours = sinhVienService.getTotalVolunteerHours(mssv);
        model.addAttribute("sinhVien", sinhVien);
        model.addAttribute("thamGias", thamGias);
        model.addAttribute("totalHours", totalHours);
        return "search-sinhvien";
    }

    // Tìm kiếm hoạt động
    @GetMapping("/search/hoatdong")
    public String searchHoatDong(@RequestParam("maHD") String maHD, Model model) {
        HoatDong hoatDong = hoatDongService.findById(maHD);
        List<ThamGia> thamGias = thamGiaService.findByHoatDong(maHD);
        model.addAttribute("hoatDong", hoatDong);
        model.addAttribute("thamGias", thamGias);
        return "search-hoatdong";
    }

    // Xuất chứng nhận PDF
//    @GetMapping("/thamgia/certificate")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STUDENT')")
//    public void generateCertificate(@RequestParam("mssv") String mssv, @RequestParam("maHD") String maHD,
//                                    HttpServletResponse response) throws Exception {
//        ThamGia thamGia = thamGiaService.findBySinhVien(mssv).stream()
//                .filter(t -> t.getMaHD().equals(maHD) && "APPROVED".equals(t.getTrangThai())).findFirst().orElse(null);
//        if (thamGia != null) {
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "attachment; filename=certificate_" + mssv + "_" + maHD + ".pdf");
//
//            Document document = new Document();
//            PdfWriter.getInstance(document, response.getOutputStream());
//            document.open();
//            document.add(new Paragraph("CHỨNG NHẬN THAM GIA"));
//            document.add(new Paragraph("Sinh Viên: " + thamGia.getSinhVien().getHoTen()));
//            document.add(new Paragraph("Hoạt Động: " + thamGia.getHoatDong().getTenHD()));
//            document.add(new Paragraph("Số Giờ: " + thamGia.getSoGioThamGia()));
//            document.add(new Paragraph("Xếp Loại: " + thamGia.getXepLoai()));
//            document.close();
//        } else {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy tham gia hoặc chưa được duyệt");
//        }
//    }

    // Xuất báo cáo Excel
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

        List<SinhVien> sinhViens = sinhVienService.findAll(Pageable.unpaged()).getContent();
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

    // Form thêm đánh giá
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
        return "danhgia-form";
    }

    @PostMapping("/danhgia/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STUDENT')")
    public String saveDanhGia(@ModelAttribute DanhGia danhGia, Authentication auth) {
        danhGiaService.save(danhGia);
        auditLogService.log(auth.getName(), "SAVE_DANHGIA", "Saved danh gia: " + danhGia.getMssv() + ", " + danhGia.getMaHD());
        return "redirect:/search/sinhvien?mssv=" + danhGia.getMssv() + "&successMessage=Thêm đánh giá thành công";
    }
}