package vn.edu.volunteer.service;

import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.ToChuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface HoatDongService {
    /**
     * Tìm kiếm hoạt động theo các tiêu chí
     */
    Page<HoatDong> findAll(String maHoatDong, String tenHoatDong, String toChuc, String trangThai, Pageable pageable);
    
    /**
     * Tìm tất cả hoạt động
     */
    Page<HoatDong> findAll(Pageable pageable);
    
    /**
     * Đếm tổng số hoạt động
     */
    long count();
    
    /**
     * Tìm hoạt động theo mã
     */
    HoatDong findByMaHoatDong(String maHoatDong);
    
    /**
     * Lưu hoạt động
     */
    HoatDong save(HoatDong hoatDong);
    
    /**
     * Xóa hoạt động
     */
    void delete(String maHoatDong);
    
    List<HoatDong> findAll();
    
    List<HoatDong> findAllWithPaging(int pageNumber, int pageSize);
    
    List<HoatDong> findByTrangThaiWithPaging(String trangThai, int pageNumber, int pageSize);
    
    List<HoatDong> searchByKeywordWithPaging(String keyword, int pageNumber, int pageSize);
    
    List<HoatDong> search(String keyword, int pageNumber, int pageSize);
    
    List<HoatDong> searchAdvanced(String maHoatDong, String tenHoatDong, String maToChuc, int pageNumber, int pageSize);
    
    long countByTrangThai(String trangThai);
    
    long countByToChuc(ToChuc toChuc);
    
    HoatDong findById(String maHoatDong);
    
    HoatDong getMostPopularActivity();

    List<HoatDong> findAvailableActivities(int page, int size);

    List<HoatDong> findByToChuc(String maToChuc, int page, int size);

    long countCertificates();
    long countCertificatesByToChuc(ToChuc toChuc);
    long countHoursByToChuc(ToChuc toChuc);
    List<HoatDong> findRecentActivities(int limit);
    List<HoatDong> findRecentActivitiesByToChuc(ToChuc toChuc, int limit);
} 