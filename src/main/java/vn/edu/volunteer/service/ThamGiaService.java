package vn.edu.volunteer.service;


import vn.edu.volunteer.dao.ThamGiaDAO;
import vn.edu.volunteer.model.ThamGia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ThamGiaService {
    @Autowired
    private ThamGiaDAO thamGiaDAO;

    @Transactional
    public void save(ThamGia thamGia) {
        thamGiaDAO.save(thamGia);
    }

    @Transactional
    public List<ThamGia> findBySinhVien(String mssv) {
        return thamGiaDAO.findBySinhVien(mssv);
    }

    @Transactional
    public List<ThamGia> findByHoatDong(String maHD) {
        return thamGiaDAO.findByHoatDong(maHD);
    }
}
