package vn.edu.volunteer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ChungNhan;
import vn.edu.volunteer.repository.SinhVienRepository;
import vn.edu.volunteer.service.SinhVienService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Override
    public Page<SinhVien> findAll(String maSinhVien, String hoTen, String lop, Pageable pageable) {
        return sinhVienRepository.findAll(maSinhVien, hoTen, lop, pageable);
    }

    @Override
    public Page<SinhVien> findAll(Pageable pageable) {
        return sinhVienRepository.findAll(pageable);
    }

    @Override
    public List<SinhVien> findAll() {
        return sinhVienRepository.findAll();
    }

    @Override
    public List<SinhVien> findAllWithPaging(int pageNumber, int pageSize) {
        return sinhVienRepository.findAll()
            .subList(pageNumber * pageSize, 
                    Math.min((pageNumber + 1) * pageSize, (int) count()));
    }

    @Override
    public List<SinhVien> search(String keyword, int pageNumber, int pageSize) {
        return sinhVienRepository.search(keyword);
    }

    @Override
    public List<SinhVien> searchAdvanced(String maSinhVien, String hoTen, int pageNumber, int pageSize) {
        // Implementation using criteria or custom query
        return null;
    }

    @Override
    public long count() {
        return sinhVienRepository.count();
    }

    @Override
    public SinhVien findById(String maSinhVien) {
        return sinhVienRepository.findById(maSinhVien).orElse(null);
    }

    @Override
    public SinhVien findByMaSinhVien(String maSinhVien) {
        return sinhVienRepository.findById(maSinhVien).orElse(null);
    }

    @Override
    public SinhVien findByUserId(String userId) {
        return sinhVienRepository.findByUser_Id(Long.parseLong(userId)).orElse(null);
    }

    @Override
    public SinhVien findByUsername(String username) {
        return sinhVienRepository.findByUser_Username(username).orElse(null);
    }

    @Override
    public Integer getTotalVolunteerHours(String maSinhVien) {
        return sinhVienRepository.getTotalVolunteerHours(maSinhVien);
    }

    @Override
    public SinhVien save(SinhVien sinhVien) {
        return sinhVienRepository.save(sinhVien);
    }

    @Override
    public SinhVien update(SinhVien sinhVien) {
        SinhVien existingSinhVien = findByMaSinhVien(sinhVien.getMaSinhVien());
        if (existingSinhVien == null) {
            throw new IllegalStateException("Không tìm thấy sinh viên để cập nhật");
        }
        
        // Preserve relationships and sensitive data
        sinhVien.setUser(existingSinhVien.getUser());
        sinhVien.setChungNhans(existingSinhVien.getChungNhans());
        sinhVien.setCreatedAt(existingSinhVien.getCreatedAt());
        
        return sinhVienRepository.save(sinhVien);
    }

    @Override
    public void delete(String maSinhVien) {
        sinhVienRepository.deleteById(maSinhVien);
    }

    @Override
    public SinhVien findByIdWithCertificates(String maSinhVien) {
        return sinhVienRepository.findByIdWithCertificates(maSinhVien).orElse(null);
    }

    @Override
    public SinhVien findByUsernameWithCertificates(String username) {
        return sinhVienRepository.findByUsernameWithCertificates(username).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChungNhan> getCertificates(String maSinhVien) {
        SinhVien sinhVien = findByIdWithCertificates(maSinhVien);
        if (sinhVien == null) {
            throw new IllegalStateException("Không tìm thấy sinh viên với mã: " + maSinhVien);
        }
        return sinhVien.getChungNhans();
    }

    @Override
    @Transactional(readOnly = true)
    public void downloadCertificate(String maSinhVien, String maCN, HttpServletResponse response) {
        // Implementation for downloading certificate
        throw new UnsupportedOperationException("Chức năng tải chứng nhận chưa được triển khai");
    }
} 