package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.HoatDong;

@Repository
public interface HoatDongRepository extends JpaRepository<HoatDong, String> {
    Page<HoatDong> findByTenHDContainingIgnoreCase(String tenHD, Pageable pageable);
    Page<HoatDong> findByDiaDiemContainingIgnoreCase(String diaDiem, Pageable pageable);
    Page<HoatDong> findByToChucMaToChuc(String maToChuc, Pageable pageable);
}