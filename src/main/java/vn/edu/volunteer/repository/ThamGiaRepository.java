package vn.edu.volunteer.repository;

import vn.edu.volunteer.model.ThamGia;
import java.util.List;

public interface ThamGiaRepository {
    List<ThamGia> findAll();
    List<ThamGia> findAllWithPaging(int pageNumber, int pageSize);
    List<ThamGia> findBySinhVien_Mssv(String mssv);
    List<ThamGia> findByHoatDong_MaHD(String maHD);
    ThamGia findBySinhVien_MssvAndHoatDong_MaHD(String mssv, String maHD);
    boolean existsBySinhVien_MssvAndHoatDong_MaHD(String mssv, String maHD);
    List<ThamGia> searchAdvanced(String mssv, String maHD, String trangThai, String xepLoai, int pageNumber, int pageSize);
    ThamGia save(ThamGia thamGia);
    void deleteById(String mssv, String maHD);
    long count();
}