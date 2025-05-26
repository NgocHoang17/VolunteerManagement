package vn.edu.volunteer.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.DanhGia;
import vn.edu.volunteer.repository.DanhGiaRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DanhGiaRepositoryImpl implements DanhGiaRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<DanhGia> findAll() {
        return getCurrentSession().createQuery("from DanhGia", DanhGia.class).list();
    }

    @Override
    public Optional<DanhGia> findById(String mssv, String maHD) {
        DanhGia danhGia = getCurrentSession()
            .createQuery("from DanhGia where mssv = :mssv and maHD = :maHD", DanhGia.class)
            .setParameter("mssv", mssv)
            .setParameter("maHD", maHD)
            .uniqueResult();
        return Optional.ofNullable(danhGia);
    }

    @Override
    public DanhGia save(DanhGia danhGia) {
        getCurrentSession().saveOrUpdate(danhGia);
        return danhGia;
    }

    @Override
    public void deleteById(String mssv, String maHD) {
        DanhGia danhGia = findById(mssv, maHD).orElse(null);
        if (danhGia != null) {
            getCurrentSession().delete(danhGia);
        }
    }

    @Override
    public List<DanhGia> findByHoatDong_MaHD(String maHD) {
        return getCurrentSession()
            .createQuery("from DanhGia where hoatDong.maHD = :maHD", DanhGia.class)
            .setParameter("maHD", maHD)
            .list();
    }

    @Override
    public List<DanhGia> findBySinhVien_Mssv(String mssv) {
        return getCurrentSession()
            .createQuery("from DanhGia where sinhVien.mssv = :mssv", DanhGia.class)
            .setParameter("mssv", mssv)
            .list();
    }

    @Override
    public double getAverageDiemByHoatDong_MaHD(String maHD) {
        Double avgDiem = getCurrentSession()
            .createQuery("select avg(diem) from DanhGia where hoatDong.maHD = :maHD", Double.class)
            .setParameter("maHD", maHD)
            .uniqueResult();
        return avgDiem != null ? avgDiem : 0.0;
    }

    @Override
    public long count() {
        return getCurrentSession()
            .createQuery("select count(*) from DanhGia", Long.class)
            .uniqueResult();
    }
} 