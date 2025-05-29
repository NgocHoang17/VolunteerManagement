package vn.edu.volunteer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.ChungNhan;
import vn.edu.volunteer.model.User;
import vn.edu.volunteer.repository.ChungNhanRepository;
import vn.edu.volunteer.service.ChungNhanService;

import java.util.Date;

@Service
@Transactional
public class ChungNhanServiceImpl implements ChungNhanService {

    @Autowired
    private ChungNhanRepository chungNhanRepository;

    @Override
    public Page<ChungNhan> findAll(String maChungNhan, String tenSinhVien, String tenHoatDong, String trangThai, Pageable pageable) {
        return chungNhanRepository.findAll(maChungNhan, tenSinhVien, tenHoatDong, trangThai, pageable);
    }

    @Override
    public void approve(String maChungNhan, User nguoiDuyet) {
        ChungNhan chungNhan = findByMaChungNhan(maChungNhan);
        if (chungNhan == null) {
            throw new RuntimeException("Không tìm thấy chứng nhận");
        }
        
        chungNhan.setTrangThai("APPROVED");
        chungNhan.setNguoiDuyet(nguoiDuyet);
        chungNhan.setNgayDuyet(new Date());
        save(chungNhan);
    }

    @Override
    public void reject(String maChungNhan, String lyDo, User nguoiDuyet) {
        ChungNhan chungNhan = findByMaChungNhan(maChungNhan);
        if (chungNhan == null) {
            throw new RuntimeException("Không tìm thấy chứng nhận");
        }
        
        chungNhan.setTrangThai("REJECTED");
        chungNhan.setNguoiDuyet(nguoiDuyet);
        chungNhan.setNgayDuyet(new Date());
        chungNhan.setLyDoTuChoi(lyDo);
        save(chungNhan);
    }

    @Override
    public ChungNhan findByMaChungNhan(String maChungNhan) {
        return chungNhanRepository.findById(maChungNhan)
                .orElse(null);
    }

    @Override
    public ChungNhan save(ChungNhan chungNhan) {
        return chungNhanRepository.save(chungNhan);
    }

    @Override
    public void delete(String maChungNhan) {
        chungNhanRepository.deleteById(maChungNhan);
    }

    @Override
    public long count() {
        return chungNhanRepository.count();
    }
} 