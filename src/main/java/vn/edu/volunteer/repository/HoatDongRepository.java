package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.ToChuc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoatDongRepository extends JpaRepository<HoatDong, String>, JpaSpecificationExecutor<HoatDong> {
    Page<HoatDong> findByTenHoatDongContainingIgnoreCase(String tenHoatDong, Pageable pageable);
    Page<HoatDong> findByDiaDiemContainingIgnoreCase(String diaDiem, Pageable pageable);
    Page<HoatDong> findByToChucMaToChuc(String maToChuc, Pageable pageable);

    @Query(value = "SELECT h FROM HoatDong h " +
           "LEFT JOIN h.thamGias tg " +
           "GROUP BY h " +
           "ORDER BY COUNT(tg) DESC",
           countQuery = "SELECT COUNT(DISTINCT h) FROM HoatDong h LEFT JOIN h.thamGias tg")
    Page<HoatDong> findAllOrderByThamGiaCount(Pageable pageable);

    @Query("SELECT h FROM HoatDong h " +
           "LEFT JOIN FETCH h.toChuc " +
           "LEFT JOIN FETCH h.thamGias tg " +
           "LEFT JOIN FETCH tg.sinhVien " +
           "WHERE h.maHoatDong = ?1")
    Optional<HoatDong> findByIdWithDetails(String maHoatDong);

    default HoatDong findMostPopularActivity() {
        Page<HoatDong> page = findAllOrderByThamGiaCount(Pageable.ofSize(1));
        if (page.hasContent()) {
            HoatDong hoatDong = page.getContent().get(0);
            return findByIdWithDetails(hoatDong.getMaHoatDong()).orElse(null);
        }
        return null;
    }

    Page<HoatDong> findByTrangThai(String trangThai, Pageable pageable);
    
    Page<HoatDong> findByTenHoatDongContaining(String keyword, Pageable pageable);
    
    Page<HoatDong> findByMaHoatDongContainingAndTenHoatDongContainingAndToChucMaToChucContaining(
        String maHoatDong, String tenHoatDong, String maToChuc, Pageable pageable);
    
    long countByTrangThai(String trangThai);
    
    Optional<HoatDong> findTopByOrderBySoLuongDaDangKyDesc();

    @Query(value = "SELECT h FROM HoatDong h " +
           "WHERE h.trangThai = :trangThai AND h.thoiGianBatDau > :now " +
           "ORDER BY h.thoiGianBatDau ASC",
           countQuery = "SELECT COUNT(h) FROM HoatDong h " +
           "WHERE h.trangThai = :trangThai AND h.thoiGianBatDau > :now")
    Page<HoatDong> findByTrangThaiAndThoiGianBatDauAfterOrderByThoiGianBatDauAsc(
        @Param("trangThai") String trangThai, 
        @Param("now") LocalDateTime now, 
        Pageable pageable);

    long countByToChuc(ToChuc toChuc);
    
    @Query("SELECT COUNT(c) FROM ChungNhan c")
    long countCertificates();
    
    @Query("SELECT COUNT(c) FROM ChungNhan c WHERE c.hoatDong.toChuc = ?1")
    long countCertificatesByToChuc(ToChuc toChuc);
    
    @Query("SELECT COALESCE(SUM(tg.soGioThamGia), 0) FROM ThamGia tg WHERE tg.hoatDong.toChuc = ?1 AND tg.trangThai = 'APPROVED'")
    long countHoursByToChuc(ToChuc toChuc);
    
    @Query("SELECT DISTINCT h FROM HoatDong h " +
           "LEFT JOIN FETCH h.toChuc " +
           "LEFT JOIN FETCH h.thamGias tg " +
           "LEFT JOIN FETCH tg.sinhVien " +
           "ORDER BY h.thoiGianBatDau DESC")
    List<HoatDong> findRecentActivities(int limit);
    
    @Query("SELECT DISTINCT h FROM HoatDong h " +
           "LEFT JOIN FETCH h.toChuc " +
           "LEFT JOIN FETCH h.thamGias tg " +
           "LEFT JOIN FETCH tg.sinhVien " +
           "WHERE h.toChuc = ?1 " +
           "ORDER BY h.thoiGianBatDau DESC")
    List<HoatDong> findRecentActivitiesByToChuc(ToChuc toChuc, int limit);

    @Query("SELECT h FROM HoatDong h " +
           "WHERE (:maHoatDong IS NULL OR h.maHoatDong LIKE %:maHoatDong%) " +
           "AND (:tenHoatDong IS NULL OR h.tenHoatDong LIKE %:tenHoatDong%) " +
           "AND (:toChuc IS NULL OR h.toChuc.tenToChuc LIKE %:toChuc%) " +
           "AND (:trangThai IS NULL OR h.trangThai = :trangThai)")
    Page<HoatDong> findAll(@Param("maHoatDong") String maHoatDong,
                          @Param("tenHoatDong") String tenHoatDong,
                          @Param("toChuc") String toChuc,
                          @Param("trangThai") String trangThai,
                          Pageable pageable);

    @Query(value = "SELECT * FROM hoat_dong LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<HoatDong> findAllWithPaging(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM hoat_dong WHERE LOWER(ten_hoat_dong) LIKE LOWER(:keyword) OR ma_hoat_dong LIKE :keyword LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<HoatDong> search(@Param("keyword") String keyword, @Param("offset") int offset, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM hoat_dong WHERE " +
           "(:maHoatDong IS NULL OR ma_hoat_dong LIKE :maHoatDong) AND " +
           "(:tenHoatDong IS NULL OR LOWER(ten_hoat_dong) LIKE LOWER(:tenHoatDong)) AND " +
           "(:maToChuc IS NULL OR ma_to_chuc = :maToChuc) " +
           "LIMIT :pageSize OFFSET :offset", nativeQuery = true)
    List<HoatDong> searchAdvanced(@Param("maHoatDong") String maHoatDong,
                                 @Param("tenHoatDong") String tenHoatDong,
                                 @Param("maToChuc") String maToChuc,
                                 @Param("offset") int offset,
                                 @Param("pageSize") int pageSize);

    @Query("SELECT h FROM HoatDong h ORDER BY h.soLuongDaDangKy DESC")
    HoatDong getMostPopularActivity();
}