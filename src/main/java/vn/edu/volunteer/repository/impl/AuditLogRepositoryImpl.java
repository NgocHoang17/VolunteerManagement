package vn.edu.volunteer.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.AuditLog;
import vn.edu.volunteer.repository.AuditLogRepository;

import java.util.List;
import java.util.Date;
import java.util.Optional;

@Repository
@Transactional
public class AuditLogRepositoryImpl implements AuditLogRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<AuditLog> findAll() {
        return getCurrentSession().createQuery("from AuditLog", AuditLog.class).list();
    }

    @Override
    public List<AuditLog> findAllWithPaging(int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        Query<AuditLog> query = session.createQuery("from AuditLog order by timestamp desc", AuditLog.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public Optional<AuditLog> findById(Long id) {
        AuditLog auditLog = getCurrentSession().get(AuditLog.class, id);
        return Optional.ofNullable(auditLog);
    }

    @Override
    public AuditLog save(AuditLog auditLog) {
        getCurrentSession().saveOrUpdate(auditLog);
        return auditLog;
    }

    @Override
    public void deleteById(Long id) {
        AuditLog auditLog = findById(id).orElse(null);
        if (auditLog != null) {
            getCurrentSession().delete(auditLog);
        }
    }

    @Override
    public List<AuditLog> findByUsername(String username) {
        return getCurrentSession()
            .createQuery("from AuditLog where username = :username order by timestamp desc", AuditLog.class)
            .setParameter("username", username)
            .list();
    }

    @Override
    public List<AuditLog> findByAction(String action) {
        return getCurrentSession()
            .createQuery("from AuditLog where action = :action order by timestamp desc", AuditLog.class)
            .setParameter("action", action)
            .list();
    }

    @Override
    public List<AuditLog> findByTimestampBetween(Date startDate, Date endDate) {
        return getCurrentSession()
            .createQuery("from AuditLog where timestamp between :startDate and :endDate order by timestamp desc", AuditLog.class)
            .setParameter("startDate", startDate)
            .setParameter("endDate", endDate)
            .list();
    }

    @Override
    public List<AuditLog> searchLogs(String username, String action, Date startDate, Date endDate, int pageNumber, int pageSize) {
        Session session = getCurrentSession();
        StringBuilder hql = new StringBuilder("from AuditLog a where 1=1");
        
        if (username != null && !username.isEmpty()) {
            hql.append(" and a.username = :username");
        }
        if (action != null && !action.isEmpty()) {
            hql.append(" and a.action = :action");
        }
        if (startDate != null) {
            hql.append(" and a.timestamp >= :startDate");
        }
        if (endDate != null) {
            hql.append(" and a.timestamp <= :endDate");
        }
        
        hql.append(" order by a.timestamp desc");
        
        Query<AuditLog> query = session.createQuery(hql.toString(), AuditLog.class);
        
        if (username != null && !username.isEmpty()) {
            query.setParameter("username", username);
        }
        if (action != null && !action.isEmpty()) {
            query.setParameter("action", action);
        }
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public long count() {
        return getCurrentSession()
            .createQuery("select count(*) from AuditLog", Long.class)
            .uniqueResult();
    }
} 