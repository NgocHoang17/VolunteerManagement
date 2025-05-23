package vn.edu.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.DanhGia;
import vn.edu.volunteer.model.ThamGiaId;
import vn.edu.volunteer.repository.DanhGiaRepository;

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
        ThamGiaId id = new ThamGiaId();
        id.setMssv(mssv);
        id.setMaHD(maHD);
        return danhGiaRepository.findById(id).orElse(null);
    }
}