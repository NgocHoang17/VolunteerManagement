package vn.edu.volunteer.dao;

import vn.edu.volunteer.model.SinhVien;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SinhVienDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(SinhVien sinhVien) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(sinhVien);
    }

    public List<SinhVien> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(SinhVien.class).list();
    }


    public SinhVien findById(String mssv) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(SinhVien.class, mssv);
    }


}