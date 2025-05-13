package vn.edu.volunteer.dao;


import vn.edu.volunteer.model.HoatDong;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HoatDongDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(HoatDong hoatDong) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(hoatDong);
    }

    public List<HoatDong> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from HoatDong", HoatDong.class).list();
    }

    public HoatDong findById(String maHD) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(HoatDong.class, maHD);
    }
}
