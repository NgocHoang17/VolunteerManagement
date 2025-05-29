package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.model.ThamGiaId;
import vn.edu.volunteer.model.ToChuc;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.HoatDong;

import java.util.List;
import java.util.Optional;

/**
 * Repository để quản lý thông tin tham gia hoạt động của sinh viên
 */
@Repository
public interface ThamGiaRepository extends JpaRepository<ThamGia, ThamGiaId> {
    // Tìm tất cả hoạt động của một sinh viên theo mã sinh viên
    List<ThamGia> findByMaSinhVien(String maSinhVien);
    
    // Tìm tất cả sinh viên tham gia một hoạt động theo mã hoạt động
    List<ThamGia> findByMaHoatDong(String maHoatDong);
    
    /**
     * Tìm kiếm nâng cao với nhiều tiêu chí
     * @param maSinhVien Mã sinh viên (tìm kiếm mờ)
     * @param maHoatDong Mã hoạt động (tìm kiếm mờ)
     * @param trangThai Trạng thái tham gia (PENDING, APPROVED, CANCELED)
     * @param xepLoai Xếp loại kết quả tham gia
     * @param pageable Thông tin phân trang
     */
    @Query("SELECT t FROM ThamGia t WHERE " +
           "(:maSinhVien IS NULL OR t.maSinhVien LIKE %:maSinhVien%) AND " +
           "(:maHoatDong IS NULL OR t.maHoatDong LIKE %:maHoatDong%) AND " +
           "(:trangThai IS NULL OR t.trangThai = :trangThai) AND " +
           "(:xepLoai IS NULL OR t.xepLoai = :xepLoai)")
    Page<ThamGia> searchAdvanced(@Param("maSinhVien") String maSinhVien, 
                                @Param("maHoatDong") String maHoatDong, 
                                @Param("trangThai") String trangThai, 
                                @Param("xepLoai") String xepLoai, 
                                Pageable pageable);
    
    // Tìm thông tin tham gia theo mã sinh viên và mã hoạt động
    @Query("SELECT t FROM ThamGia t WHERE t.maSinhVien = :maSinhVien AND t.maHoatDong = :maHoatDong")
    Optional<ThamGia> findById(@Param("maSinhVien") String maSinhVien, @Param("maHoatDong") String maHoatDong);
    
    // Xóa thông tin tham gia theo mã sinh viên và mã hoạt động
    @Query("DELETE FROM ThamGia t WHERE t.maSinhVien = :maSinhVien AND t.maHoatDong = :maHoatDong")
    void deleteById(@Param("maSinhVien") String maSinhVien, @Param("maHoatDong") String maHoatDong);
    
    // Đếm số hoạt động của một sinh viên
    long countByMaSinhVien(String maSinhVien);
    
    // Tính tổng số giờ tham gia hoạt động đã được duyệt của sinh viên
    @Query("SELECT COALESCE(SUM(t.soGioThamGia), 0) FROM ThamGia t WHERE t.maSinhVien = :maSinhVien AND t.trangThai = 'APPROVED'")
    int countHoursByMaSinhVien(@Param("maSinhVien") String maSinhVien);
    
    // Đếm số hoạt động đã được duyệt của sinh viên (tính điểm rèn luyện)
    @Query("SELECT COALESCE(COUNT(t), 0) FROM ThamGia t WHERE t.maSinhVien = :maSinhVien AND t.trangThai = 'APPROVED'")
    int countPointsByMaSinhVien(@Param("maSinhVien") String maSinhVien);
    
    // Đếm số chứng nhận của sinh viên
    @Query("SELECT COUNT(c) FROM ThamGia t JOIN t.sinhVien.chungNhans c WHERE t.maSinhVien = :maSinhVien")
    long countCertificatesByMaSinhVien(@Param("maSinhVien") String maSinhVien);

    // Lấy danh sách hoạt động của sinh viên, sắp xếp theo ngày đăng ký giảm dần
    Page<ThamGia> findByMaSinhVienOrderByNgayDangKyDesc(String maSinhVien, Pageable pageable);

    // Đếm số lượng tình nguyện viên của một tổ chức
    @Query("SELECT COUNT(DISTINCT t.sinhVien) FROM ThamGia t WHERE t.hoatDong.toChuc = ?1")
    long countVolunteersByToChuc(ToChuc toChuc);
    
    // Lấy danh sách tình nguyện viên gần đây của một tổ chức
    @Query("SELECT t FROM ThamGia t WHERE t.hoatDong.toChuc = ?1 ORDER BY t.ngayDangKy DESC")
    List<ThamGia> findRecentVolunteersByToChuc(ToChuc toChuc, int limit);

    // Lấy danh sách hoạt động gần đây của một sinh viên
    @Query("SELECT t FROM ThamGia t WHERE t.maSinhVien = :maSinhVien ORDER BY t.ngayDangKy DESC")
    List<ThamGia> findRecentActivities(String maSinhVien, int limit, Pageable pageable);

    // Check if a student has already registered for an activity
    boolean existsBySinhVienAndHoatDong(SinhVien sinhVien, HoatDong hoatDong);

    // Find registration by student and activity
    Optional<ThamGia> findBySinhVienAndHoatDong(SinhVien sinhVien, HoatDong hoatDong);
}