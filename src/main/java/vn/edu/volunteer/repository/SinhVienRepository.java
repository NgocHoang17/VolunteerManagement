package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.SinhVien;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    Page<SinhVien> findByHoTenContainingIgnoreCase(String hoTen, Pageable pageable);
    Page<SinhVien> findByEmailContainingIgnoreCase(String email, Pageable pageable);
    Page<SinhVien> findByTruongMaTruong(String maTruong, Pageable pageable);
}