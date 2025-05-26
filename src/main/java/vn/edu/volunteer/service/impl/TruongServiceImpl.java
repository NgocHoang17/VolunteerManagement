package vn.edu.volunteer.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.Truong;
import vn.edu.volunteer.service.TruongService;

import java.util.List;

@Service
@Transactional
public class TruongServiceImpl implements TruongService {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Truong> findAll() {
        return getCurrentSession().createQuery("from Truong", Truong.class).list();
    }

    @Override
    public List<Truong> findAllWithPaging(int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        Query<Truong> query = session.createQuery("from Truong", Truong.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<Truong> search(String keyword, int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        StringBuilder hql = new StringBuilder("from Truong t where 1=1");
        
        if (keyword != null && !keyword.isEmpty()) {
            hql.append(" and (lower(t.tenTruong) like lower(:keyword) or t.maTruong like :keyword)");
        }
        
        Query<Truong> query = session.createQuery(hql.toString(), Truong.class);
        
        if (keyword != null && !keyword.isEmpty()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public Truong findById(String maTruong) {
        return getCurrentSession().get(Truong.class, maTruong);
    }

    @Override
    public Truong save(Truong truong) {
        getCurrentSession().saveOrUpdate(truong);
        return truong;
    }

    @Override
    public void deleteById(String maTruong) {
        Truong truong = findById(maTruong);
        if (truong != null) {
            getCurrentSession().delete(truong);
        }
    }

    @Override
    public long count() {
        return getCurrentSession()
            .createQuery("select count(*) from Truong", Long.class)
            .uniqueResult();
    }
} 