package vn.edu.volunteer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ChungNhan;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface SinhVienService {
    /**
     * Tìm kiếm sinh viên theo các tiêu chí
     */
    Page<SinhVien> findAll(String maSinhVien, String hoTen, String lop, Pageable pageable);
    
    /**
     * Tìm tất cả sinh viên với phân trang
     */
    Page<SinhVien> findAll(Pageable pageable);
    
    /**
     * Tìm tất cả sinh viên
     */
    List<SinhVien> findAll();
    
    /**
     * Tìm sinh viên với phân trang
     */
    List<SinhVien> findAllWithPaging(int pageNumber, int pageSize);
    
    /**
     * Tìm kiếm sinh viên theo từ khóa
     */
    List<SinhVien> search(String keyword, int pageNumber, int pageSize);
    
    /**
     * Tìm kiếm sinh viên nâng cao
     */
    List<SinhVien> searchAdvanced(String maSinhVien, String hoTen, int pageNumber, int pageSize);
    
    /**
     * Đếm tổng số sinh viên
     */
    long count();
    
    /**
     * Tìm sinh viên theo mã
     */
    SinhVien findById(String maSinhVien);
    
    /**
     * Tìm sinh viên theo mã
     */
    SinhVien findByMaSinhVien(String maSinhVien);
    
    /**
     * Tìm sinh viên theo mã với chứng nhận
     */
    SinhVien findByIdWithCertificates(String maSinhVien);
    
    /**
     * Tìm sinh viên theo username
     */
    SinhVien findByUsername(String username);
    
    /**
     * Tìm sinh viên theo username với chứng nhận
     */
    SinhVien findByUsernameWithCertificates(String username);
    
    /**
     * Tìm sinh viên theo user ID
     */
    SinhVien findByUserId(String userId);
    
    /**
     * Lấy tổng số giờ tham gia hoạt động
     */
    Integer getTotalVolunteerHours(String maSinhVien);
    
    /**
     * Lấy danh sách chứng nhận của sinh viên
     */
    List<ChungNhan> getCertificates(String maSinhVien);
    
    /**
     * Lưu thông tin sinh viên
     */
    SinhVien save(SinhVien sinhVien);
    
    /**
     * Cập nhật thông tin sinh viên
     */
    SinhVien update(SinhVien sinhVien);
    
    /**
     * Xóa sinh viên
     */
    void delete(String maSinhVien);
    
    /**
     * Tải chứng nhận
     */
    void downloadCertificate(String maSinhVien, String maCN, HttpServletResponse response);
}