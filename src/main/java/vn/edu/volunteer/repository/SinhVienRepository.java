package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.SinhVien;

import java.util.List;
import java.util.Optional;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    @Query("SELECT s FROM SinhVien s JOIN FETCH s.user u LEFT JOIN FETCH u.authorities LEFT JOIN FETCH s.chungNhans WHERE u.username = :username")
    Optional<SinhVien> findByUser_Username(@Param("username") String username);
    
    Optional<SinhVien> findByUser_Id(Long userId);
    
    @Query("SELECT s FROM SinhVien s WHERE LOWER(s.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) OR s.maSinhVien LIKE CONCAT('%', :keyword, '%')")
    List<SinhVien> search(@Param("keyword") String keyword);
    
    @Query("SELECT COALESCE(SUM(t.soGioThamGia), 0) FROM ThamGia t WHERE t.sinhVien.maSinhVien = :maSinhVien AND t.trangThai = 'APPROVED'")
    Integer getTotalVolunteerHours(@Param("maSinhVien") String maSinhVien);

    @Query("SELECT s FROM SinhVien s LEFT JOIN FETCH s.chungNhans WHERE s.maSinhVien = :maSinhVien")
    Optional<SinhVien> findByIdWithCertificates(@Param("maSinhVien") String maSinhVien);

    @Query("SELECT s FROM SinhVien s JOIN FETCH s.user u LEFT JOIN FETCH u.authorities LEFT JOIN FETCH s.chungNhans WHERE u.username = :username")
    Optional<SinhVien> findByUsernameWithCertificates(@Param("username") String username);

    @Query("SELECT s FROM SinhVien s " +
           "WHERE (:maSinhVien IS NULL OR s.maSinhVien LIKE %:maSinhVien%) " +
           "AND (:hoTen IS NULL OR s.hoTen LIKE %:hoTen%) " +
           "AND (:lop IS NULL OR s.lop LIKE %:lop%)")
    Page<SinhVien> findAll(@Param("maSinhVien") String maSinhVien,
                          @Param("hoTen") String hoTen,
                          @Param("lop") String lop,
                          Pageable pageable);
}