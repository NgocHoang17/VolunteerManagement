package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.volunteer.model.ChungNhan;

public interface ChungNhanRepository extends JpaRepository<ChungNhan, String> {
    
    @Query("SELECT c FROM ChungNhan c " +
           "WHERE (:maChungNhan IS NULL OR c.maChungNhan LIKE %:maChungNhan%) " +
           "AND (:tenSinhVien IS NULL OR c.sinhVien.hoTen LIKE %:tenSinhVien%) " +
           "AND (:tenHoatDong IS NULL OR c.hoatDong.tenHoatDong LIKE %:tenHoatDong%) " +
           "AND (:trangThai IS NULL OR c.trangThai = :trangThai)")
    Page<ChungNhan> findAll(@Param("maChungNhan") String maChungNhan,
                           @Param("tenSinhVien") String tenSinhVien,
                           @Param("tenHoatDong") String tenHoatDong,
                           @Param("trangThai") String trangThai,
                           Pageable pageable);
} 