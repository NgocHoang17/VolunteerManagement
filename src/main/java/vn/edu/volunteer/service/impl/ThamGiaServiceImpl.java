package vn.edu.volunteer.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.model.ThamGiaId;
import vn.edu.volunteer.model.ToChuc;
import vn.edu.volunteer.repository.ThamGiaRepository;
import vn.edu.volunteer.service.HoatDongService;
import vn.edu.volunteer.service.SinhVienService;
import vn.edu.volunteer.service.ThamGiaService;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ThamGiaServiceImpl implements ThamGiaService {

    @Autowired
    private ThamGiaRepository thamGiaRepository;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private HoatDongService hoatDongService;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<ThamGia> findAll() {
        return thamGiaRepository.findAll();
    }

    @Override
    public List<ThamGia> findAllWithPaging(int pageNumber, int pageSize) {
        return thamGiaRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    @Override
    public List<ThamGia> findBySinhVien(String maSinhVien) {
        return thamGiaRepository.findByMaSinhVien(maSinhVien);
    }

    @Override
    public List<ThamGia> findByHoatDong(String maHD) {
        return thamGiaRepository.findByMaHoatDong(maHD);
    }

    @Override
    public List<ThamGia> searchAdvanced(String maSinhVien, String maHD, String trangThai, String xepLoai, int pageNumber, int pageSize) {
        return thamGiaRepository.searchAdvanced(maSinhVien, maHD, trangThai, xepLoai, PageRequest.of(pageNumber, pageSize)).getContent();
    }

    @Override
    public ThamGia findById(String maSinhVien, String maHD) {
        return thamGiaRepository.findById(maSinhVien, maHD).orElse(null);
    }

    @Override
    public ThamGia save(ThamGia thamGia) {
        return thamGiaRepository.save(thamGia);
    }

    @Override
    public void deleteById(String maSinhVien, String maHD) {
        thamGiaRepository.deleteById(maSinhVien, maHD);
    }

    @Override
    public void approveThamGia(String maSinhVien, String maHD) {
        ThamGia thamGia = thamGiaRepository.findById(maSinhVien, maHD).orElse(null);
        if (thamGia != null) {
            thamGia.setTrangThai("APPROVED");
            thamGia.setNgayDuyet(LocalDateTime.now());
            thamGiaRepository.save(thamGia);
            
            // Gửi email thông báo nếu có cấu hình email
            if (mailSender != null) {
                SinhVien sinhVien = sinhVienService.findById(maSinhVien);
                if (sinhVien != null && sinhVien.getEmail() != null) {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(sinhVien.getEmail());
                    message.setSubject("Đăng ký tham gia hoạt động đã được duyệt");
                    message.setText("Xin chào " + sinhVien.getHoTen() + ",\n\n"
                        + "Đăng ký tham gia hoạt động của bạn đã được duyệt.\n"
                        + "Mã hoạt động: " + maHD + "\n"
                        + "Vui lòng kiểm tra thông tin chi tiết trên hệ thống.\n\n"
                        + "Trân trọng,\nBan quản lý");
                    mailSender.send(message);
                }
            }
        }
    }

    @Override
    public long count() {
        return thamGiaRepository.count();
    }

    @Override
    public long countActivities(String maSinhVien) {
        return thamGiaRepository.countByMaSinhVien(maSinhVien);
    }

    @Override
    public int countHours(String maSinhVien) {
        return thamGiaRepository.countHoursByMaSinhVien(maSinhVien);
    }

    @Override
    public int countPoints(String maSinhVien) {
        return thamGiaRepository.countPointsByMaSinhVien(maSinhVien);
    }

    @Override
    public long countCertificates(String maSinhVien) {
        return thamGiaRepository.countCertificatesByMaSinhVien(maSinhVien);
    }

    @Override
    public void register(String maSinhVien, String maHD) {
        ThamGia thamGia = new ThamGia();
        thamGia.setMaSinhVien(maSinhVien);
        thamGia.setMaHoatDong(maHD);
        thamGia.setTrangThai("PENDING");
        thamGiaRepository.save(thamGia);
    }

    @Override
    public void cancel(String maSinhVien, String maHD) {
        ThamGia thamGia = thamGiaRepository.findById(maSinhVien, maHD).orElse(null);
        if (thamGia != null) {
            thamGia.setTrangThai("CANCELED");
            thamGiaRepository.save(thamGia);
            
            // Gửi email thông báo nếu có cấu hình email
            if (mailSender != null) {
                SinhVien sinhVien = sinhVienService.findById(maSinhVien);
                HoatDong hoatDong = hoatDongService.findById(maHD);
                
                if (sinhVien != null && sinhVien.getEmail() != null) {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setTo(sinhVien.getEmail());
                    message.setSubject("Hủy đăng ký tham gia hoạt động thành công");
                    message.setText("Xin chào " + sinhVien.getHoTen() + ",\n\n"
                        + "Bạn đã hủy đăng ký tham gia hoạt động sau:\n"
                        + "Tên hoạt động: " + (hoatDong != null ? hoatDong.getTenHoatDong() : maHD) + "\n"
                        + "Mã hoạt động: " + maHD + "\n\n"
                        + "Nếu bạn muốn đăng ký lại, vui lòng truy cập hệ thống.\n\n"
                        + "Trân trọng,\nBan quản lý");
                    mailSender.send(message);
                }
            }
        }
    }

    @Override
    public List<ThamGia> findByMaSinhVien(String maSinhVien, int page, int size) {
        return thamGiaRepository.findByMaSinhVienOrderByNgayDangKyDesc(maSinhVien, PageRequest.of(page, size)).getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public long countVolunteersByToChuc(ToChuc toChuc) {
        return thamGiaRepository.countVolunteersByToChuc(toChuc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThamGia> findRecentVolunteersByToChuc(ToChuc toChuc, int limit) {
        return thamGiaRepository.findRecentVolunteersByToChuc(toChuc, limit);
    }

    @Override
    public List<ThamGia> findRecentActivities(String maSinhVien, int limit) {
        return thamGiaRepository.findRecentActivities(maSinhVien, limit, PageRequest.of(0, limit));
    }

    @Override
    @Transactional(readOnly = true)
    public long countByStudent(SinhVien sinhVien) {
        return thamGiaRepository.countByMaSinhVien(sinhVien.getMaSinhVien());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThamGia> findRecentActivitiesByStudent(SinhVien sinhVien, int limit) {
        return thamGiaRepository.findByMaSinhVienOrderByNgayDangKyDesc(
            sinhVien.getMaSinhVien(), 
            PageRequest.of(0, limit)
        ).getContent();
    }

    @Override
    public ThamGia dangKyThamGia(String maSinhVien, String maHoatDong) {
        // Kiểm tra sinh viên tồn tại
        SinhVien sinhVien = sinhVienService.findByMaSinhVien(maSinhVien);
        if (sinhVien == null) {
            throw new IllegalStateException("Không tìm thấy sinh viên");
        }

        // Kiểm tra hoạt động tồn tại
        HoatDong hoatDong = hoatDongService.findByMaHoatDong(maHoatDong);
        if (hoatDong == null) {
            throw new IllegalStateException("Không tìm thấy hoạt động");
        }

        // Kiểm tra đã đăng ký chưa
        if (thamGiaRepository.existsBySinhVienAndHoatDong(sinhVien, hoatDong)) {
            throw new IllegalStateException("Sinh viên đã đăng ký hoạt động này");
        }

        // Kiểm tra số lượng đăng ký
        if (hoatDong.getSoLuongDaDangKy() >= hoatDong.getSoLuongToiDa()) {
            throw new IllegalStateException("Hoạt động đã đủ số lượng đăng ký");
        }

        // Tạo đăng ký mới
        ThamGia thamGia = new ThamGia();
        thamGia.setSinhVien(sinhVien);
        thamGia.setHoatDong(hoatDong);
        thamGia.setTrangThai("PENDING");
        thamGia.setNgayDangKy(LocalDateTime.now());

        // Cập nhật số lượng đăng ký
        hoatDong.setSoLuongDaDangKy(hoatDong.getSoLuongDaDangKy() + 1);
        hoatDongService.save(hoatDong);

        return thamGiaRepository.save(thamGia);
    }

    @Override
    public void huyDangKy(String maSinhVien, String maHoatDong) {
        // Kiểm tra sinh viên tồn tại
        SinhVien sinhVien = sinhVienService.findByMaSinhVien(maSinhVien);
        if (sinhVien == null) {
            throw new IllegalStateException("Không tìm thấy sinh viên");
        }

        // Kiểm tra hoạt động tồn tại
        HoatDong hoatDong = hoatDongService.findByMaHoatDong(maHoatDong);
        if (hoatDong == null) {
            throw new IllegalStateException("Không tìm thấy hoạt động");
        }

        // Tìm đăng ký
        ThamGia thamGia = thamGiaRepository.findBySinhVienAndHoatDong(sinhVien, hoatDong)
            .orElseThrow(() -> new IllegalStateException("Không tìm thấy đăng ký"));

        // Kiểm tra trạng thái
        if (!"PENDING".equals(thamGia.getTrangThai())) {
            throw new IllegalStateException("Không thể hủy đăng ký ở trạng thái này");
        }

        // Xóa đăng ký
        thamGiaRepository.delete(thamGia);

        // Cập nhật số lượng đăng ký
        hoatDong.setSoLuongDaDangKy(hoatDong.getSoLuongDaDangKy() - 1);
        hoatDongService.save(hoatDong);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThamGia> findVolunteersByToChuc(ToChuc toChuc, int page, int size) {
        return getCurrentSession()
            .createQuery(
                "select distinct t from ThamGia t " +
                "join fetch t.sinhVien s " +
                "join fetch t.hoatDong h " +
                "where h.toChuc = :toChuc " +
                "and t.trangThai = 'APPROVED' " +
                "order by t.ngayDangKy desc", ThamGia.class)
            .setParameter("toChuc", toChuc)
            .setFirstResult(page * size)
            .setMaxResults(size)
            .list();
    }
} 