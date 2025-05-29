package vn.edu.volunteer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.volunteer.model.ToChuc;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ThamGia;
import java.util.List;
import java.util.Map;

public interface ToChucService {
    /**
     * Tìm kiếm tổ chức theo các tiêu chí
     */
    Page<ToChuc> findAll(String maToChuc, String tenToChuc, String diaChi, ToChuc.TrangThaiXacThuc trangThai, Pageable pageable);
    
    /**
     * Tìm tất cả tổ chức
     */
    Page<ToChuc> findAll(Pageable pageable);
    
    /**
     * Đếm tổng số tổ chức
     */
    long count();
    
    /**
     * Tìm tổ chức theo mã
     */
    ToChuc findByMaToChuc(String maToChuc);
    
    /**
     * Lưu tổ chức
     */
    ToChuc save(ToChuc toChuc);
    
    /**
     * Xóa tổ chức
     */
    void delete(String maToChuc);

    List<ToChuc> findAllWithPaging(int pageNumber, int pageSize);
    
    List<ToChuc> search(String keyword, int pageNumber, int pageSize);
    
    ToChuc findById(String maToChuc);
    
    void deleteById(String maToChuc);

    ToChuc findByUserId(String userId);

    List<HoatDong> getActivities(String maToChuc);

    List<SinhVien> getVolunteers(String maToChuc);

    Map<String, Object> getStatistics(String maToChuc);

    ToChuc update(ToChuc toChuc);

    HoatDong createActivity(HoatDong hoatDong);

    List<SinhVien> getActivityVolunteers(String maToChuc, String maHoatDong);

    void approveVolunteer(String maHoatDong, String maSinhVien);

    void rejectVolunteer(String maHoatDong, String maSinhVien);

    List<ThamGia> getVolunteerHistory(String maToChuc, String maSV);

    void blockVolunteer(String maToChuc, String maSV);

    void unblockVolunteer(String maToChuc, String maSV);

    ToChuc findByEmail(String email);
}