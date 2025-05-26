package vn.edu.volunteer.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.HoatDong;
import vn.edu.volunteer.service.HoatDongService;

import java.util.List;

@Service
@Transactional
public class HoatDongServiceImpl implements HoatDongService {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<HoatDong> findAll() {
        return getCurrentSession().createQuery("from HoatDong", HoatDong.class).list();
    }

    @Override
    public List<HoatDong> findAllWithPaging(int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        Query<HoatDong> query = session.createQuery("from HoatDong", HoatDong.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<HoatDong> findByTrangThaiWithPaging(String trangThai, int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        Query<HoatDong> query = session.createQuery(
            "from HoatDong where trangThai = :trangThai", 
            HoatDong.class
        );
        query.setParameter("trangThai", trangThai);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<HoatDong> searchByKeywordWithPaging(String keyword, int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        Query<HoatDong> query = session.createQuery(
            "from HoatDong where tenHD like :keyword or moTa like :keyword", 
            HoatDong.class
        );
        query.setParameter("keyword", "%" + keyword + "%");
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<HoatDong> search(String keyword, String maToChuc, int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        StringBuilder hql = new StringBuilder("from HoatDong h where 1=1");
        
        if (keyword != null && !keyword.isEmpty()) {
            hql.append(" and (h.tenHD like :keyword or h.diaDiem like :keyword)");
        }
        if (maToChuc != null && !maToChuc.isEmpty()) {
            hql.append(" and h.toChuc.maToChuc = :maToChuc");
        }
        
        Query<HoatDong> query = session.createQuery(hql.toString(), HoatDong.class);
        
        if (keyword != null && !keyword.isEmpty()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        if (maToChuc != null && !maToChuc.isEmpty()) {
            query.setParameter("maToChuc", maToChuc);
        }
        
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<HoatDong> searchAdvanced(String maHD, String tenHD, String maToChuc, int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        StringBuilder hql = new StringBuilder("from HoatDong h where 1=1");
        
        if (maHD != null && !maHD.isEmpty()) {
            hql.append(" and h.maHD like :maHD");
        }
        if (tenHD != null && !tenHD.isEmpty()) {
            hql.append(" and lower(h.tenHD) like lower(:tenHD)");
        }
        if (maToChuc != null && !maToChuc.isEmpty()) {
            hql.append(" and h.toChuc.maToChuc = :maToChuc");
        }
        
        Query<HoatDong> query = session.createQuery(hql.toString(), HoatDong.class);
        
        if (maHD != null && !maHD.isEmpty()) {
            query.setParameter("maHD", "%" + maHD + "%");
        }
        if (tenHD != null && !tenHD.isEmpty()) {
            query.setParameter("tenHD", "%" + tenHD + "%");
        }
        if (maToChuc != null && !maToChuc.isEmpty()) {
            query.setParameter("maToChuc", maToChuc);
        }
        
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public long count() {
        return getCurrentSession()
            .createQuery("select count(*) from HoatDong", Long.class)
            .uniqueResult();
    }

    @Override
    public long countByTrangThai(String trangThai) {
        return getCurrentSession()
            .createQuery("select count(*) from HoatDong where trangThai = :trangThai", Long.class)
            .setParameter("trangThai", trangThai)
            .uniqueResult();
    }

    @Override
    public HoatDong findById(String maHD) {
        return getCurrentSession().get(HoatDong.class, maHD);
    }

    @Override
    public HoatDong save(HoatDong hoatDong) {
        getCurrentSession().saveOrUpdate(hoatDong);
        return hoatDong;
    }

    @Override
    public void deleteById(String maHD) {
        HoatDong hoatDong = findById(maHD);
        if (hoatDong != null) {
            getCurrentSession().delete(hoatDong);
        }
    }

    @Override
    public HoatDong getMostPopularActivity() {
        return getCurrentSession()
            .createQuery(
                "from HoatDong h order by size(h.thamGias) desc", 
                HoatDong.class
            )
            .setMaxResults(1)
            .uniqueResult();
    }
} 