package vn.edu.volunteer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.ToChuc;

@Repository
public interface ToChucRepository extends JpaRepository<ToChuc, String> {
    Page<ToChuc> findByTenToChucContainingIgnoreCase(String tenToChuc, Pageable pageable);
}