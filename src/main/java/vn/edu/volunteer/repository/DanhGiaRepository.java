package vn.edu.volunteer.repository;

import vn.edu.volunteer.model.DanhGia;
import java.util.List;
import java.util.Optional;

public interface DanhGiaRepository {
    List<DanhGia> findAll();
    Optional<DanhGia> findById(String mssv, String maHD);
    DanhGia save(DanhGia danhGia);
    void deleteById(String mssv, String maHD);
    List<DanhGia> findByHoatDong_MaHD(String maHD);
    List<DanhGia> findBySinhVien_Mssv(String mssv);
    double getAverageDiemByHoatDong_MaHD(String maHD);
    long count();
}