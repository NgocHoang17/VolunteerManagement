package vn.edu.volunteer.service;

import vn.edu.volunteer.model.SinhVien;
import java.util.List;

public interface SinhVienService {
    List<SinhVien> findAll();
    
    List<SinhVien> findAllWithPaging(int pageNumber, int pageSize);
    
    List<SinhVien> search(String keyword, String maTruong, int pageNumber, int pageSize);
    
    List<SinhVien> searchAdvanced(String mssv, String hoTen, String maTruong, int pageNumber, int pageSize);
    
    SinhVien findById(String mssv);
    
    SinhVien save(SinhVien sinhVien);
    
    void deleteById(String mssv);
    
    long count();
    
    int getTotalVolunteerHours(String mssv);
}