package vn.edu.volunteer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.DanhGia;
import vn.edu.volunteer.model.ThamGiaId;

@Repository
public interface DanhGiaRepository extends JpaRepository<DanhGia, ThamGiaId> {
}