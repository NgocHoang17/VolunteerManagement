package vn.edu.volunteer.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.ToChuc;
import vn.edu.volunteer.service.ToChucService;

import java.util.List;

@Service
@Transactional
public class ToChucServiceImpl implements ToChucService {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<ToChuc> findAll() {
        return getCurrentSession().createQuery("from ToChuc", ToChuc.class).list();
    }

    @Override
    public List<ToChuc> findAllWithPaging(int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        Query<ToChuc> query = session.createQuery("from ToChuc", ToChuc.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public List<ToChuc> search(String keyword, int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        StringBuilder hql = new StringBuilder("from ToChuc t where 1=1");
        
        if (keyword != null && !keyword.isEmpty()) {
            hql.append(" and (lower(t.tenToChuc) like lower(:keyword) or t.maToChuc like :keyword)");
        }
        
        Query<ToChuc> query = session.createQuery(hql.toString(), ToChuc.class);
        
        if (keyword != null && !keyword.isEmpty()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }
        
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public ToChuc findById(String maToChuc) {
        return getCurrentSession().get(ToChuc.class, maToChuc);
    }

    @Override
    public ToChuc save(ToChuc toChuc) {
        getCurrentSession().saveOrUpdate(toChuc);
        return toChuc;
    }

    @Override
    public void deleteById(String maToChuc) {
        ToChuc toChuc = findById(maToChuc);
        if (toChuc != null) {
            getCurrentSession().delete(toChuc);
        }
    }

    @Override
    public long count() {
        return getCurrentSession()
            .createQuery("select count(*) from ToChuc", Long.class)
            .uniqueResult();
    }
} 