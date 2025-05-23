package vn.edu.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.ToChuc;
import vn.edu.volunteer.repository.ToChucRepository;

@Service
public class ToChucService {
    @Autowired
    private ToChucRepository toChucRepository;

    @Transactional
    public void save(ToChuc toChuc) {
        toChucRepository.save(toChuc);
    }

    @Cacheable("toChucs")
    @Transactional(readOnly = true)
    public Page<ToChuc> findAll(Pageable pageable) {
        return toChucRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public ToChuc findById(String maToChuc) {
        return toChucRepository.findById(maToChuc).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<ToChuc> search(String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return toChucRepository.findByTenToChucContainingIgnoreCase(keyword, pageable);
        }
        return findAll(pageable);
    }
}