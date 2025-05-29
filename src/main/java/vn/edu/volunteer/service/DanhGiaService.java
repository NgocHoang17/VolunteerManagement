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
    public DanhGia findById(String maSinhVien, String maHoatDong) {
        return danhGiaRepository.findById(maSinhVien, maHoatDong).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<DanhGia> findByHoatDong(String maHoatDong) {
        return danhGiaRepository.findByHoatDong_MaHD(maHoatDong);
    }

    @Transactional(readOnly = true)
    public List<DanhGia> findBySinhVien(String maSinhVien) {
        return danhGiaRepository.findBySinhVien_Mssv(maSinhVien);
    }

    @Transactional(readOnly = true)
    public double getAverageDiemByHoatDong(String maHoatDong) {
        return danhGiaRepository.getAverageDiemByHoatDong_MaHD(maHoatDong);
    }
}