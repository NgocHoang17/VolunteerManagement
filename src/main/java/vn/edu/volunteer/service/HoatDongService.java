package vn.edu.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.repository.HoatDongRepository;

@Service
public class HoatDongService {
    @Autowired
    private HoatDongRepository hoatDongRepository;

    @Transactional
    public void save(HoatDong hoatDong) {
        hoatDongRepository.save(hoatDong);
    }

    @Cacheable("hoatDongs")
    @Transactional(readOnly = true)
    public Page<HoatDong> findAll(Pageable pageable) {
        return hoatDongRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public HoatDong findById(String maHD) {
        return hoatDongRepository.findById(maHD).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<HoatDong> search(String keyword, String maToChuc, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return hoatDongRepository.findByTenHDContainingIgnoreCase(keyword, pageable);
        }
        if (maToChuc != null && !maToChuc.isEmpty()) {
            return hoatDongRepository.findByToChucMaToChuc(maToChuc, pageable);
        }
        return findAll(pageable);
    }

    @Transactional(readOnly = true)
    public String getMostPopularActivity() {
        return hoatDongRepository.findAll().stream()
                .max((h1, h2) -> Integer.compare(
                        h1.getThamGias() != null ? h1.getThamGias().size() : 0,
                        h2.getThamGias() != null ? h2.getThamGias().size() : 0))
                .map(HoatDong::getTenHD)
                .orElse("Chưa có hoạt động");
    }

    @Transactional
    public void deleteById(String maHD) {
        hoatDongRepository.deleteById(maHD);
    }
}