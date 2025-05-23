package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.Truong;

@Repository
public interface TruongRepository extends JpaRepository<Truong, String> {
    Page<Truong> findByTenTruongContainingIgnoreCase(String tenTruong, Pageable pageable);
    Page<Truong> findByKhuVuc(String khuVuc, Pageable pageable);
}