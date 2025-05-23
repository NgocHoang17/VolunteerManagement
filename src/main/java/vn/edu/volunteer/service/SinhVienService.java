package vn.edu.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.repository.SinhVienRepository;
import vn.edu.volunteer.repository.ThamGiaRepository;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;

    @Autowired
    private ThamGiaRepository thamGiaRepository;

    @Transactional
    public void save(SinhVien sinhVien) {
        sinhVienRepository.save(sinhVien);
    }

    @Cacheable("sinhViens")
    @Transactional(readOnly = true)
    public Page<SinhVien> findAll(Pageable pageable) {
        return sinhVienRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public SinhVien findById(String mssv) {
        return sinhVienRepository.findById(mssv).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<SinhVien> search(String keyword, String maTruong, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            if (keyword.contains("@")) {
                return sinhVienRepository.findByEmailContainingIgnoreCase(keyword, pageable);
            }
            return sinhVienRepository.findByHoTenContainingIgnoreCase(keyword, pageable);
        }
        if (maTruong != null && !maTruong.isEmpty()) {
            return sinhVienRepository.findByTruongMaTruong(maTruong, pageable);
        }
        return findAll(pageable);
    }

    @Transactional(readOnly = true)
    public int getTotalVolunteerHours(String mssv) {
        return thamGiaRepository.findByMssv(mssv).stream()
                .filter(tg -> "APPROVED".equals(tg.getTrangThai()))
                .mapToInt(ThamGia::getSoGioThamGia)
                .sum();
    }

    @Transactional
    public void deleteById(String mssv) {
        sinhVienRepository.deleteById(mssv);
    }
}