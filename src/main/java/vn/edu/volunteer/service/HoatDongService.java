package vn.edu.volunteer.service;

import vn.edu.volunteer.model.HoatDong;
import java.util.List;

public interface HoatDongService {
    List<HoatDong> findAll();
    
    List<HoatDong> findAllWithPaging(int pageNumber, int pageSize);
    
    List<HoatDong> findByTrangThaiWithPaging(String trangThai, int pageNumber, int pageSize);
    
    List<HoatDong> searchByKeywordWithPaging(String keyword, int pageNumber, int pageSize);
    
    List<HoatDong> search(String keyword, String maToChuc, int pageNumber, int pageSize);
    
    List<HoatDong> searchAdvanced(String maHD, String tenHD, String maToChuc, int pageNumber, int pageSize);
    
    long count();
    
    long countByTrangThai(String trangThai);
    
    HoatDong findById(String maHD);
    
    HoatDong save(HoatDong hoatDong);
    
    void deleteById(String maHD);
    
    HoatDong getMostPopularActivity();
} 