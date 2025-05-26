package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.SinhVien;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String>, JpaSpecificationExecutor<SinhVien> {
    Page<SinhVien> findByHoTenContainingIgnoreCase(String hoTen, Pageable pageable);
    Page<SinhVien> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<SinhVien> findByTruongMaTruong(String maTruong, Pageable pageable);

    @Query("SELECT COALESCE(SUM(tg.soGioThamGia), 0) FROM ThamGia tg " +
           "WHERE tg.sinhVien.mssv = :mssv AND tg.trangThai = 'APPROVED'")
    int calculateTotalVolunteerHours(@Param("mssv") String mssv);
}