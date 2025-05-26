package vn.edu.volunteer.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.SinhVien;
import vn.edu.volunteer.service.SinhVienService;

import java.util.List;

@Service
@Transactional
public class SinhVienServiceImpl implements SinhVienService {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<SinhVien> findAll() {
        return getCurrentSession().createQuery("from SinhVien", SinhVien.class).list();
    }

    @Override
    public List<SinhVien> findAllWithPaging(int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        Query<SinhVien> query = session.createQuery("from SinhVien", SinhVien.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<SinhVien> search(String keyword, String maTruong, int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        StringBuilder hql = new StringBuilder("from SinhVien s where 1=1");
        
        if (keyword != null && !keyword.isEmpty()) {
            hql.append(" and (lower(s.hoTen) like lower(:keyword) or s.mssv like :keyword)");
        }
        if (maTruong != null && !maTruong.isEmpty()) {
            hql.append(" and s.truong.maTruong = :maTruong");
        }
        
        Query<SinhVien> query = session.createQuery(hql.toString(), SinhVien.class);
        
        if (keyword != null && !keyword.isEmpty()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        if (maTruong != null && !maTruong.isEmpty()) {
            query.setParameter("maTruong", maTruong);
        }
        
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public SinhVien findById(String mssv) {
        return getCurrentSession().get(SinhVien.class, mssv);
    }

    @Override
    public SinhVien save(SinhVien sinhVien) {
        getCurrentSession().saveOrUpdate(sinhVien);
        return sinhVien;
    }

    @Override
    public void deleteById(String mssv) {
        SinhVien sinhVien = findById(mssv);
        if (sinhVien != null) {
            getCurrentSession().delete(sinhVien);
        }
    }

    @Override
    public long count() {
        return getCurrentSession()
            .createQuery("select count(*) from SinhVien", Long.class)
            .uniqueResult();
    }

    @Override
    public List<SinhVien> searchAdvanced(String mssv, String hoTen, String maTruong, int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        StringBuilder hql = new StringBuilder("from SinhVien s where 1=1");
        
        if (mssv != null && !mssv.isEmpty()) {
            hql.append(" and s.mssv like :mssv");
        }
        if (hoTen != null && !hoTen.isEmpty()) {
            hql.append(" and lower(s.hoTen) like lower(:hoTen)");
        }
        if (maTruong != null && !maTruong.isEmpty()) {
            hql.append(" and s.truong.maTruong = :maTruong");
        }
        
        Query<SinhVien> query = session.createQuery(hql.toString(), SinhVien.class);
        
        if (mssv != null && !mssv.isEmpty()) {
            query.setParameter("mssv", "%" + mssv + "%");
        }
        if (hoTen != null && !hoTen.isEmpty()) {
            query.setParameter("hoTen", "%" + hoTen + "%");
        }
        if (maTruong != null && !maTruong.isEmpty()) {
            query.setParameter("maTruong", maTruong);
        }
        
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public int getTotalVolunteerHours(String mssv) {
        return getCurrentSession()
            .createQuery("select coalesce(sum(t.soGioThamGia), 0) from ThamGia t where t.sinhVien.mssv = :mssv and t.trangThai = 'APPROVED'", Integer.class)
            .setParameter("mssv", mssv)
            .uniqueResult();
    }
} 