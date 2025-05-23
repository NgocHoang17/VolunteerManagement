package vn.edu.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.Truong;
import vn.edu.volunteer.repository.TruongRepository;

@Service
public class TruongService {
    @Autowired
    private TruongRepository truongRepository;

    @Transactional
    public void save(Truong truong) {
        truongRepository.save(truong);
    }

    @Cacheable("truongs")
    @Transactional(readOnly = true)
    public Page<Truong> findAll(Pageable pageable) {
        return truongRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Truong findById(String maTruong) {
        return truongRepository.findById(maTruong).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<Truong> search(String keyword, String khuVuc, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return truongRepository.findByTenTruongContainingIgnoreCase(keyword, pageable);
        }
        if (khuVuc != null && !khuVuc.isEmpty()) {
            return truongRepository.findByKhuVuc(khuVuc, pageable);
        }
        return findAll(pageable);
    }
}