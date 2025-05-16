package vn.edu.volunteer.controller;


import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.service.HoatDongService;
import vn.edu.volunteer.service.SinhVienService;
import vn.edu.volunteer.service.ThamGiaService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // Trang chủ
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("sinhViens", sinhVienService.findAll());
        model.addAttribute("hoatDongs", hoatDongService.findAll());
        model.addAttribute("mostPopularActivity", hoatDongService.getMostPopularActivity());
        return "index";
    }

    // Form thêm sinh viên
    @GetMapping("/sinhvien/new")
    public String showSinhVienForm(Model model) {
        model.addAttribute("sinhVien", new SinhVien());
        return "sinhvien-form";
    }

    @PostMapping("/sinhvien/save")
    public String saveSinhVien(@ModelAttribute SinhVien sinhVien) {
        sinhVienService.save(sinhVien);
        return "redirect:/";
    }

    // Form thêm hoạt động
    @GetMapping("/hoatdong/new")
    public String showHoatDongForm(Model model) {
        model.addAttribute("hoatDong", new HoatDong());
        return "hoatdong-form";
    }

    @PostMapping("/hoatdong/save")
    public String saveHoatDong(@ModelAttribute HoatDong hoatDong) {
        hoatDongService.save(hoatDong);
        return "redirect:/";
    }

    // Form thêm tham gia
    @GetMapping("/thamgia/new")
    public String showThamGiaForm(Model model) {
        model.addAttribute("thamGia", new ThamGia());
        model.addAttribute("sinhViens", sinhVienService.findAll());
        model.addAttribute("hoatDongs", hoatDongService.findAll());
        return "thamgia-form";
    }

    @PostMapping("/thamgia/save")
    public String saveThamGia(@ModelAttribute ThamGia thamGia) {
        // Kiểm tra hợp lệ thời gian
        HoatDong hoatDong = hoatDongService.findById(thamGia.getMaHD());
        if (hoatDong.getThoiGianKetThuc().before(new Date())) {
            thamGiaService.save(thamGia);
            return "redirect:/";
        }
        return "error";
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

    // Chỉnh sửa sinh viên
    @GetMapping("/sinhvien/edit/{mssv}")
    public String showEditSinhVienForm(@PathVariable String mssv, Model model) {
        SinhVien sinhVien = sinhVienService.findById(mssv);
        model.addAttribute("sinhVien", sinhVien);
        return "sinhvien-form";
    }

    // Xóa sinh viên
//    @GetMapping("/sinhvien/delete/{mssv}")
//    public String deleteSinhVien(@PathVariable String mssv) {
//        sinhVienService.deleteById(mssv);
//        return "redirect:/?successMessage=Xóa sinh viên thành công";
//    }

    // Chỉnh sửa hoạt động
    @GetMapping("/hoatdong/edit/{maHD}")
    public String showEditHoatDongForm(@PathVariable String maHD, Model model) {
        HoatDong hoatDong = hoatDongService.findById(maHD);
        model.addAttribute("hoatDong", hoatDong);
        return "hoatdong-form";
    }

    // Xóa hoạt động
//    @GetMapping("/hoatdong/delete/{maHD}")
//    public String deleteHoatDong(@PathVariable String maHD) {
//        hoatDongService.deleteById(maHD);
//        return "redirect:/?successMessage=Xóa hoạt động thành công";
//    }

    // Xuất chứng nhận PDF
    @GetMapping("/thamgia/certificate")
    public void generateCertificate(@RequestParam("mssv") String mssv, @RequestParam("maHD") String maHD, HttpServletResponse response) throws Exception {
        ThamGia thamGia = thamGiaService.findBySinhVien(mssv).stream()
                .filter(t -> t.getMaHD().equals(maHD)).findFirst().orElse(null);
        if (thamGia != null) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=certificate.pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            document.add(new Paragraph("CHỨNG NHẬN THAM GIA"));
            document.add(new Paragraph("Sinh Viên: " + thamGia.getSinhVien().getHoTen()));
            document.add(new Paragraph("Hoạt Động: " + thamGia.getHoatDong().getTenHD()));
            document.add(new Paragraph("Số Giờ: " + thamGia.getSoGioThamGia()));
            document.close();
        }
    }
}