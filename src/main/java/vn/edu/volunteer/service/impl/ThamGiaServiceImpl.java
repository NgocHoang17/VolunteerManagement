package vn.edu.volunteer.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.model.ThamGiaId;
import vn.edu.volunteer.repository.ThamGiaRepository;
import vn.edu.volunteer.service.HoatDongService;
import vn.edu.volunteer.service.SinhVienService;
import vn.edu.volunteer.service.ThamGiaService;

import javax.persistence.criteria.Predicate;
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
        return getCurrentSession().createQuery("from ThamGia", ThamGia.class).list();
    }

    @Override
    public List<ThamGia> findAllWithPaging(int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        Query<ThamGia> query = session.createQuery("from ThamGia", ThamGia.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<ThamGia> findBySinhVien(String mssv) {
        return getCurrentSession()
            .createQuery("from ThamGia where sinhVien.mssv = :mssv", ThamGia.class)
            .setParameter("mssv", mssv)
            .list();
    }

    @Override
    public List<ThamGia> findByHoatDong(String maHD) {
        return getCurrentSession()
            .createQuery("from ThamGia where hoatDong.maHD = :maHD", ThamGia.class)
            .setParameter("maHD", maHD)
            .list();
    }

    @Override
    public List<ThamGia> searchAdvanced(String mssv, String maHD, String trangThai, String xepLoai, int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        StringBuilder hql = new StringBuilder("from ThamGia t where 1=1");
        
        if (mssv != null && !mssv.isEmpty()) {
            hql.append(" and t.sinhVien.mssv = :mssv");
        }
        if (maHD != null && !maHD.isEmpty()) {
            hql.append(" and t.hoatDong.maHD = :maHD");
        }
        if (trangThai != null && !trangThai.isEmpty()) {
            hql.append(" and t.trangThai = :trangThai");
        }
        if (xepLoai != null && !xepLoai.isEmpty()) {
            hql.append(" and t.xepLoai = :xepLoai");
        }
        
        Query<ThamGia> query = session.createQuery(hql.toString(), ThamGia.class);
        
        if (mssv != null && !mssv.isEmpty()) {
            query.setParameter("mssv", mssv);
        }
        if (maHD != null && !maHD.isEmpty()) {
            query.setParameter("maHD", maHD);
        }
        if (trangThai != null && !trangThai.isEmpty()) {
            query.setParameter("trangThai", trangThai);
        }
        if (xepLoai != null && !xepLoai.isEmpty()) {
            query.setParameter("xepLoai", xepLoai);
        }
        
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public ThamGia findById(String mssv, String maHD) {
        return getCurrentSession()
            .createQuery("from ThamGia where sinhVien.mssv = :mssv and hoatDong.maHD = :maHD", ThamGia.class)
            .setParameter("mssv", mssv)
            .setParameter("maHD", maHD)
            .uniqueResult();
    }

    @Override
    public ThamGia save(ThamGia thamGia) {
        HoatDong hoatDong = hoatDongService.findById(thamGia.getMaHD());
        if (hoatDong.getThoiGianKetThuc().after(new Date())) {
            thamGia.setTrangThai("PENDING");
            getCurrentSession().saveOrUpdate(thamGia);
            sendEmailNotification(thamGia);
            return thamGia;
        } else {
            throw new IllegalStateException("Hoạt động đã kết thúc");
        }
    }

    @Override
    public void deleteById(String mssv, String maHD) {
        ThamGia thamGia = findById(mssv, maHD);
        if (thamGia != null) {
            getCurrentSession().delete(thamGia);
        }
    }

    @Override
    public void approveThamGia(String mssv, String maHD) {
        ThamGia thamGia = findById(mssv, maHD);
        if (thamGia != null) {
            thamGia.setTrangThai("APPROVED");
            getCurrentSession().update(thamGia);
        }
    }

    @Override
    public long count() {
        return getCurrentSession()
            .createQuery("select count(*) from ThamGia", Long.class)
            .uniqueResult();
    }

    private void sendEmailNotification(ThamGia thamGia) {
        if (mailSender != null && thamGia.getSinhVien() != null && thamGia.getSinhVien().getEmail() != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(thamGia.getSinhVien().getEmail());
            message.setSubject("Đăng ký tham gia hoạt động thành công");
            message.setText("Bạn đã đăng ký tham gia hoạt động " + thamGia.getHoatDong().getTenHD() + 
                          ". Vui lòng chờ xác nhận từ ban tổ chức.");
            mailSender.send(message);
        }
    }
} 