package vn.edu.volunteer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.model.ThamGiaId;
import vn.edu.volunteer.repository.ThamGiaRepository;

import java.util.Date;
import java.util.List;

@Service
public class ThamGiaService {
    @Autowired
    private ThamGiaRepository thamGiaRepository;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private HoatDongService hoatDongService;

    @Autowired (required = false)
    private JavaMailSender mailSender;

    @Transactional
    public void save(ThamGia thamGia) {
        HoatDong hoatDong = hoatDongService.findById(thamGia.getMaHD());
        if (hoatDong.getThoiGianKetThuc().after(new Date())) {
            thamGia.setTrangThai("PENDING");
            thamGiaRepository.save(thamGia);
            sendEmailNotification(thamGia);
        } else {
            throw new IllegalStateException("Hoạt động đã kết thúc");
        }
    }

    @Transactional(readOnly = true)
    public List<ThamGia> findBySinhVien(String mssv) {
        return thamGiaRepository.findByMssv(mssv);
    }

    @Transactional(readOnly = true)
    public List<ThamGia> findByHoatDong(String maHD) {
        return thamGiaRepository.findByMaHD(maHD);
    }

    @Transactional
    public void approveThamGia(String mssv, String maHD) {
        ThamGiaId id = new ThamGiaId();
        id.setMssv(mssv);
        id.setMaHD(maHD);
        ThamGia thamGia = thamGiaRepository.findById(id).orElse(null);
        if (thamGia != null) {
            thamGia.setTrangThai("APPROVED");
            thamGiaRepository.save(thamGia);
        }
    }

    private void sendEmailNotification(ThamGia thamGia) {
        SinhVien sinhVien = sinhVienService.findById(thamGia.getMssv());
        HoatDong hoatDong = hoatDongService.findById(thamGia.getMaHD());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sinhVien.getEmail());
        message.setSubject("Đăng ký tham gia hoạt động tình nguyện");
        message.setText("Bạn đã đăng ký tham gia hoạt động: " + hoatDong.getTenHD() +
                ". Vui lòng chờ duyệt.");
        mailSender.send(message);
    }
}