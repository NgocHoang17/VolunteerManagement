package vn.edu.volunteer.service;

import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.model.SinhVien;
import java.util.List;
import vn.edu.volunteer.model.ToChuc;

public interface ThamGiaService {
    List<ThamGia> findAll();
    
    List<ThamGia> findAllWithPaging(int pageNumber, int pageSize);
    
    List<ThamGia> findBySinhVien(String maSinhVien);
    
    List<ThamGia> findByHoatDong(String maHD);
    
    List<ThamGia> searchAdvanced(String maSinhVien, String maHD, String trangThai, String xepLoai, int pageNumber, int pageSize);
    
    ThamGia findById(String maSinhVien, String maHD);
    
    ThamGia save(ThamGia thamGia);
    
    void deleteById(String maSinhVien, String maHD);
    
    void approveThamGia(String maSinhVien, String maHD);
    
    long count();
    
    long countActivities(String maSinhVien);

    int countHours(String maSinhVien);
    
    int countPoints(String maSinhVien);

    long countCertificates(String maSinhVien);

    void register(String maSinhVien, String maHD);

    void cancel(String maSinhVien, String maHD);

    List<ThamGia> findByMaSinhVien(String maSinhVien, int page, int size);

    long countVolunteersByToChuc(ToChuc toChuc);
    
    List<ThamGia> findRecentVolunteersByToChuc(ToChuc toChuc, int limit);

    List<ThamGia> findRecentActivities(String maSinhVien, int limit);

    long countByStudent(SinhVien sinhVien);

    List<ThamGia> findRecentActivitiesByStudent(SinhVien sinhVien, int limit);

    ThamGia dangKyThamGia(String maSinhVien, String maHoatDong);

    void huyDangKy(String maSinhVien, String maHoatDong);
} 