package vn.edu.volunteer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.volunteer.model.ChungNhan;
import vn.edu.volunteer.model.User;

public interface ChungNhanService {
    /**
     * Tìm kiếm chứng nhận theo các tiêu chí
     */
    Page<ChungNhan> findAll(String maChungNhan, String tenSinhVien, String tenHoatDong, String trangThai, Pageable pageable);
    
    /**
     * Duyệt chứng nhận
     */
    void approve(String maChungNhan, User nguoiDuyet);
    
    /**
     * Từ chối chứng nhận
     */
    void reject(String maChungNhan, String lyDo, User nguoiDuyet);
    
    /**
     * Tìm chứng nhận theo mã
     */
    ChungNhan findByMaChungNhan(String maChungNhan);
    
    /**
     * Lưu chứng nhận
     */
    ChungNhan save(ChungNhan chungNhan);
    
    /**
     * Xóa chứng nhận
     */
    void delete(String maChungNhan);

    /**
     * Đếm tổng số chứng nhận
     */
    long count();
} 