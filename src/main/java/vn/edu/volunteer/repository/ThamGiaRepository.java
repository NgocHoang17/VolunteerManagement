package vn.edu.volunteer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.model.ThamGiaId;

import java.util.List;

@Repository
public interface ThamGiaRepository extends JpaRepository<ThamGia, ThamGiaId> {
    List<ThamGia> findByMssv(String mssv);
    List<ThamGia> findByMaHD(String maHD);
}