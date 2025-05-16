package vn.edu.volunteer.service;


import vn.edu.volunteer.dao.SinhVienDAO;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.dao.ThamGiaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SinhVienService {
    @Autowired
    private SinhVienDAO sinhVienDAO;

    @Autowired
    private ThamGiaDAO thamGiaDAO;

    @Transactional
    public void save(SinhVien sinhVien) {
        sinhVienDAO.save(sinhVien);
    }

    @Transactional
    public List<SinhVien> findAll() {
        return sinhVienDAO.findAll();
    }

    @Transactional
    public SinhVien findById(String mssv) {
        return sinhVienDAO.findById(mssv);
    }

//    public void deleteById(String id) {
//        sinhVienDAO.deleteById(id);
//    }

    @Transactional
    public int getTotalVolunteerHours(String mssv) {
        List<ThamGia> thamGias = thamGiaDAO.findBySinhVien(mssv);
        return thamGias.stream().mapToInt(ThamGia::getSoGioThamGia).sum();
    }
}