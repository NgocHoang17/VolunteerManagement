package vn.edu.volunteer.service;


import vn.edu.volunteer.dao.HoatDongDAO;
import vn.edu.volunteer.model.HoatDong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class HoatDongService {
    @Autowired
    private HoatDongDAO hoatDongDAO;

    @Transactional
    public void save(HoatDong hoatDong) {
        hoatDongDAO.save(hoatDong);
    }

    @Transactional
    public List<HoatDong> findAll() {
        return hoatDongDAO.findAll();
    }

    @Transactional
    public HoatDong findById(String maHD) {
        return hoatDongDAO.findById(maHD);
    }

//    @Transactional
//    public String getMostPopularActivity() {
//        List<HoatDong> hoatDongs = hoatDongDAO.findAll();
//        return hoatDongs.stream()
//                .max(Comparator.comparing(h -> h.getThamGias().size()))
//                .map(H RoslynCore::get(HoatDong::getTenHD)
//                .orElse("Chưa có hoạt động");
//    }
}