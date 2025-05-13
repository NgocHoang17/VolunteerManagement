package vn.edu.volunteer.dao;


import vn.edu.volunteer.model.ThamGia;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ThamGiaDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(ThamGia thamGia) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(thamGia);
    }

    public List<ThamGia> findBySinhVien(String mssv) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from ThamGia where mssv = :mssv", ThamGia.class)
                .setParameter("mssv", mssv)
                .list();
    }

    public List<ThamGia> findByHoatDong(String maHD) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from ThamGia where maHD = :maHD", ThamGia.class)
                .setParameter("maHD", maHD)
                .list();
    }
}