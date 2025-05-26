package vn.edu.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.DanhGia;
import vn.edu.volunteer.repository.DanhGiaRepository;
import java.util.List;

@Service
public class DanhGiaService {
    @Autowired
    private DanhGiaRepository danhGiaRepository;

    @Transactional
    public void save(DanhGia danhGia) {
        danhGiaRepository.save(danhGia);
    }

    @Transactional(readOnly = true)
    public DanhGia findById(String mssv, String maHD) {
        return danhGiaRepository.findById(mssv, maHD).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<DanhGia> findByHoatDong(String maHD) {
        return danhGiaRepository.findByHoatDong_MaHD(maHD);
    }

    @Transactional(readOnly = true)
    public List<DanhGia> findBySinhVien(String mssv) {
        return danhGiaRepository.findBySinhVien_Mssv(mssv);
    }

    @Transactional(readOnly = true)
    public double getAverageDiemByHoatDong(String maHD) {
        return danhGiaRepository.getAverageDiemByHoatDong_MaHD(maHD);
    }
}