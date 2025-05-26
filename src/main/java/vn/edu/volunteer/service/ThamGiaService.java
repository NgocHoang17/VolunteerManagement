package vn.edu.volunteer.service;

import vn.edu.volunteer.model.ThamGia;
import java.util.List;

public interface ThamGiaService {
    List<ThamGia> findAll();
    
    List<ThamGia> findAllWithPaging(int pageNumber, int pageSize);
    
    List<ThamGia> findBySinhVien(String mssv);
    
    List<ThamGia> findByHoatDong(String maHD);
    
    List<ThamGia> searchAdvanced(String mssv, String maHD, String trangThai, String xepLoai, int pageNumber, int pageSize);
    
    ThamGia findById(String mssv, String maHD);
    
    ThamGia save(ThamGia thamGia);
    
    void deleteById(String mssv, String maHD);
    
    void approveThamGia(String mssv, String maHD);
    
    long count();
} 