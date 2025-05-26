package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.HoatDong;

@Repository
public interface HoatDongRepository extends JpaRepository<HoatDong, String>, JpaSpecificationExecutor<HoatDong> {
    Page<HoatDong> findByTenHDContainingIgnoreCase(String tenHD, Pageable pageable);
    Page<HoatDong> findByDiaDiemContainingIgnoreCase(String diaDiem, Pageable pageable);
    Page<HoatDong> findByToChucMaToChuc(String maToChuc, Pageable pageable);

    @Query("SELECT h FROM HoatDong h " +
           "LEFT JOIN h.thamGias tg " +
           "GROUP BY h " +
           "ORDER BY COUNT(tg) DESC")
    Page<HoatDong> findAllOrderByThamGiaCount(Pageable pageable);

    default HoatDong findMostPopularActivity() {
        Page<HoatDong> page = findAllOrderByThamGiaCount(Pageable.ofSize(1));
        return page.hasContent() ? page.getContent().get(0) : null;
    }
}