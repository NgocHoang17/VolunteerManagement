package vn.edu.volunteer.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.ToChuc;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.model.User;
import vn.edu.volunteer.service.ToChucService;
import vn.edu.volunteer.service.HoatDongService;
import vn.edu.volunteer.service.SinhVienService;
import vn.edu.volunteer.service.ThamGiaService;
import vn.edu.volunteer.repository.ToChucRepository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

@Service
@Transactional
public class ToChucServiceImpl implements ToChucService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HoatDongService hoatDongService;

    @Autowired
    private SinhVienService sinhVienService;

    @Autowired
    private ThamGiaService thamGiaService;

    @Autowired
    private ToChucRepository toChucRepository;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Page<ToChuc> findAll(String maToChuc, String tenToChuc, String diaChi, ToChuc.TrangThaiXacThuc trangThai, Pageable pageable) {
        return toChucRepository.findAll(maToChuc, tenToChuc, diaChi, trangThai != null ? trangThai.name() : null, pageable);
    }

    @Override
    public Page<ToChuc> findAll(Pageable pageable) {
        return toChucRepository.findAll(pageable);
    }

    @Override
    public List<ToChuc> findAllWithPaging(int pageNumber, int pageSize) {
        return toChucRepository.findAllWithPaging(pageNumber, pageSize);
    }

    @Override
    public List<ToChuc> search(String keyword, int pageNumber, int pageSize) {
        return toChucRepository.search(keyword, pageNumber, pageSize);
    }

    @Override
    public ToChuc findById(String maToChuc) {
        return toChucRepository.findById(maToChuc).orElse(null);
    }

    @Override
    public ToChuc findByMaToChuc(String maToChuc) {
        return toChucRepository.findById(maToChuc).orElse(null);
    }

    @Override
    public ToChuc save(ToChuc toChuc) {
        return toChucRepository.save(toChuc);
    }

    @Override
    public void delete(String maToChuc) {
        toChucRepository.deleteById(maToChuc);
    }

    @Override
    public void deleteById(String maToChuc) {
        toChucRepository.deleteById(maToChuc);
    }

    @Override
    public long count() {
        return toChucRepository.count();
    }

    @Override
    public ToChuc findByUserId(String userId) {
        return toChucRepository.findByUserId(userId);
    }

    @Override
    public List<HoatDong> getActivities(String maToChuc) {
        return getCurrentSession()
            .createQuery("from HoatDong h where h.toChuc.maToChuc = :maToChuc order by h.thoiGianBatDau desc", HoatDong.class)
            .setParameter("maToChuc", maToChuc)
            .list();
    }

    @Override
    public List<SinhVien> getVolunteers(String maToChuc) {
        return getCurrentSession()
            .createQuery("select distinct t.sinhVien from ThamGia t where t.hoatDong.toChuc.maToChuc = :maToChuc", SinhVien.class)
            .setParameter("maToChuc", maToChuc)
            .list();
    }

    @Override
    public Map<String, Object> getStatistics(String maToChuc) {
        Map<String, Object> stats = new HashMap<>();
        
        // Total activities
        Long totalActivities = getCurrentSession()
            .createQuery("select count(*) from HoatDong where toChuc.maToChuc = :maToChuc", Long.class)
            .setParameter("maToChuc", maToChuc)
            .uniqueResult();
        stats.put("totalActivities", totalActivities);
        
        // Active activities
        Long activeActivities = getCurrentSession()
            .createQuery("select count(*) from HoatDong where toChuc.maToChuc = :maToChuc and thoiGianKetThuc > :now", Long.class)
            .setParameter("maToChuc", maToChuc)
            .setParameter("now", LocalDateTime.now())
            .uniqueResult();
        stats.put("activeActivities", activeActivities);
        
        // Total volunteers
        Long totalVolunteers = getCurrentSession()
            .createQuery("select count(distinct t.sinhVien.maSinhVien) from ThamGia t where t.hoatDong.toChuc.maToChuc = :maToChuc", Long.class)
            .setParameter("maToChuc", maToChuc)
            .uniqueResult();
        stats.put("totalVolunteers", totalVolunteers);
        
        // Total volunteer hours
        Long totalHours = getCurrentSession()
            .createQuery("select coalesce(sum(t.soGioThamGia), 0) from ThamGia t where t.hoatDong.toChuc.maToChuc = :maToChuc and t.trangThai = 'APPROVED'", Long.class)
            .setParameter("maToChuc", maToChuc)
            .uniqueResult();
        stats.put("totalHours", totalHours);
        
        // Average rating
        Double avgRating = getCurrentSession()
            .createQuery("select coalesce(avg(d.diem), 0) from DanhGia d where d.hoatDong.toChuc.maToChuc = :maToChuc", Double.class)
            .setParameter("maToChuc", maToChuc)
            .uniqueResult();
        stats.put("averageRating", avgRating);
        
        return stats;
    }

    @Override
    public ToChuc update(ToChuc toChuc) {
        ToChuc existingToChuc = findById(toChuc.getMaToChuc());
        if (existingToChuc == null) {
            throw new IllegalStateException("Tổ chức không tồn tại");
        }
        
        // Preserve the existing relationships and sensitive data
        toChuc.setUser(existingToChuc.getUser());
        toChuc.setHoatDongs(existingToChuc.getHoatDongs());
        toChuc.setCreatedAt(existingToChuc.getCreatedAt());
        
        getCurrentSession().merge(toChuc);
        return toChuc;
    }

    @Override
    public HoatDong createActivity(HoatDong hoatDong) {
        // Validate organization exists
        ToChuc toChuc = findById(hoatDong.getToChuc().getMaToChuc());
        if (toChuc == null) {
            throw new IllegalStateException("Tổ chức không tồn tại");
        }

        // Set initial values
        hoatDong.setTrangThai(HoatDong.TrangThaiHoatDong.CHUA_DIEN_RA);
        hoatDong.setSoLuongDaDangKy(0);
        
        // Save the activity
        getCurrentSession().save(hoatDong);
        
        // Update organization's activities list
        toChuc.getHoatDongs().add(hoatDong);
        getCurrentSession().update(toChuc);
        
        return hoatDong;
    }

    @Override
    public List<SinhVien> getActivityVolunteers(String maToChuc, String maHD) {
        return getCurrentSession()
            .createQuery(
                "select t.sinhVien from ThamGia t " +
                "where t.hoatDong.maHoatDong = :maHD " +
                "and t.hoatDong.toChuc.maToChuc = :maToChuc " +
                "order by t.createdAt", SinhVien.class)
            .setParameter("maHD", maHD)
            .setParameter("maToChuc", maToChuc)
            .list();
    }

    @Override
    public void approveVolunteer(String maHD, String maSV) {
        ThamGia thamGia = getCurrentSession()
            .createQuery("from ThamGia t where t.hoatDong.maHoatDong = :maHD and t.sinhVien.maSinhVien = :maSV", ThamGia.class)
            .setParameter("maHD", maHD)
            .setParameter("maSV", maSV)
            .uniqueResult();

        if (thamGia == null) {
            throw new IllegalStateException("Không tìm thấy thông tin đăng ký");
        }

        // Verify organization ownership
        if (!thamGia.getHoatDong().getToChuc().getMaToChuc().equals(thamGia.getHoatDong().getToChuc().getMaToChuc())) {
            throw new IllegalStateException("Không có quyền phê duyệt tình nguyện viên này");
        }

        thamGia.setTrangThai("APPROVED");
        getCurrentSession().update(thamGia);
    }

    @Override
    public void rejectVolunteer(String maHD, String maSV) {
        ThamGia thamGia = getCurrentSession()
            .createQuery("from ThamGia t where t.hoatDong.maHoatDong = :maHD and t.sinhVien.maSinhVien = :maSV", ThamGia.class)
            .setParameter("maHD", maHD)
            .setParameter("maSV", maSV)
            .uniqueResult();

        if (thamGia == null) {
            throw new IllegalStateException("Không tìm thấy thông tin đăng ký");
        }

        // Verify organization ownership
        if (!thamGia.getHoatDong().getToChuc().getMaToChuc().equals(thamGia.getHoatDong().getToChuc().getMaToChuc())) {
            throw new IllegalStateException("Không có quyền từ chối tình nguyện viên này");
        }

        thamGia.setTrangThai("REJECTED");
        getCurrentSession().update(thamGia);

        // Update activity registration count
        HoatDong hoatDong = thamGia.getHoatDong();
        hoatDong.setSoLuongDaDangKy(hoatDong.getSoLuongDaDangKy() - 1);
        getCurrentSession().update(hoatDong);
    }

    @Override
    public List<ThamGia> getVolunteerHistory(String maToChuc, String maSV) {
        return getCurrentSession()
            .createQuery(
                "from ThamGia t where t.hoatDong.toChuc.maToChuc = :maToChuc " +
                "and t.sinhVien.maSinhVien = :maSV " +
                "order by t.createdAt desc", ThamGia.class)
            .setParameter("maToChuc", maToChuc)
            .setParameter("maSV", maSV)
            .list();
    }

    @Override
    public void blockVolunteer(String maToChuc, String maSV) {
        SinhVien sinhVien = sinhVienService.findByMaSinhVien(maSV);
        if (sinhVien == null) {
            throw new IllegalStateException("Không tìm thấy sinh viên");
        }

        // Check if there are any approved participations with this organization
        Long participationCount = getCurrentSession()
            .createQuery("select count(*) from ThamGia t where t.hoatDong.toChuc.maToChuc = :maToChuc and t.sinhVien.maSinhVien = :maSV", Long.class)
            .setParameter("maToChuc", maToChuc)
            .setParameter("maSV", maSV)
            .uniqueResult();

        if (participationCount == 0) {
            throw new IllegalStateException("Sinh viên chưa từng tham gia hoạt động của tổ chức");
        }

        // Block the volunteer's user account for this organization
        User user = sinhVien.getUser();
        user.setBlocked(true);
        getCurrentSession().update(user);
    }

    @Override
    public void unblockVolunteer(String maToChuc, String maSV) {
        SinhVien sinhVien = sinhVienService.findByMaSinhVien(maSV);
        if (sinhVien == null) {
            throw new IllegalStateException("Không tìm thấy sinh viên");
        }

        // Check if there are any approved participations with this organization
        Long participationCount = getCurrentSession()
            .createQuery("select count(*) from ThamGia t where t.hoatDong.toChuc.maToChuc = :maToChuc and t.sinhVien.maSinhVien = :maSV", Long.class)
            .setParameter("maToChuc", maToChuc)
            .setParameter("maSV", maSV)
            .uniqueResult();

        if (participationCount == 0) {
            throw new IllegalStateException("Sinh viên chưa từng tham gia hoạt động của tổ chức");
        }

        // Unblock the volunteer's user account for this organization
        User user = sinhVien.getUser();
        user.setBlocked(false);
        getCurrentSession().update(user);
    }

    @Override
    @Transactional(readOnly = true)
    public ToChuc findByEmail(String email) {
        return toChucRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy tổ chức với email: " + email));
    }
} 