package vn.edu.volunteer.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.ThamGia;
import vn.edu.volunteer.repository.ThamGiaRepository;
import java.util.List;

@Repository
@Transactional
public class ThamGiaRepositoryImpl implements ThamGiaRepository {

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
    public List<ThamGia> findBySinhVien_Mssv(String mssv) {
        return getCurrentSession()
            .createQuery("from ThamGia where sinhVien.mssv = :mssv", ThamGia.class)
            .setParameter("mssv", mssv)
            .list();
    }

    @Override
    public List<ThamGia> findByHoatDong_MaHD(String maHD) {
        return getCurrentSession()
            .createQuery("from ThamGia where hoatDong.maHD = :maHD", ThamGia.class)
            .setParameter("maHD", maHD)
            .list();
    }

    @Override
    public ThamGia findBySinhVien_MssvAndHoatDong_MaHD(String mssv, String maHD) {
        return getCurrentSession()
            .createQuery("from ThamGia where sinhVien.mssv = :mssv and hoatDong.maHD = :maHD", ThamGia.class)
            .setParameter("mssv", mssv)
            .setParameter("maHD", maHD)
            .uniqueResult();
    }

    @Override
    public boolean existsBySinhVien_MssvAndHoatDong_MaHD(String mssv, String maHD) {
        Long count = getCurrentSession()
            .createQuery("select count(*) from ThamGia where sinhVien.mssv = :mssv and hoatDong.maHD = :maHD", Long.class)
            .setParameter("mssv", mssv)
            .setParameter("maHD", maHD)
            .uniqueResult();
        return count != null && count > 0;
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
    public ThamGia save(ThamGia thamGia) {
        getCurrentSession().saveOrUpdate(thamGia);
        return thamGia;
    }

    @Override
    public void deleteById(String mssv, String maHD) {
        ThamGia thamGia = findBySinhVien_MssvAndHoatDong_MaHD(mssv, maHD);
        if (thamGia != null) {
            getCurrentSession().delete(thamGia);
        }
    }

    @Override
    public long count() {
        return getCurrentSession()
            .createQuery("select count(*) from ThamGia", Long.class)
            .uniqueResult();
    }
} 