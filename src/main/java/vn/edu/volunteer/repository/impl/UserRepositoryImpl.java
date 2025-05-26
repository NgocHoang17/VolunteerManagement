package vn.edu.volunteer.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.volunteer.model.User;
import vn.edu.volunteer.repository.UserRepository;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public User findByUsername(String username) {
        Query<User> query = getCurrentSession().createQuery(
            "from User where username = :username",
            User.class
        );
        query.setParameter("username", username);
        return query.uniqueResult();
    }

    @Override
    public User findByEmail(String email) {
        Query<User> query = getCurrentSession().createQuery(
            "from User where email = :email",
            User.class
        );
        query.setParameter("email", email);
        return query.uniqueResult();
    }

    @Override
    public User save(User user) {
        getCurrentSession().saveOrUpdate(user);
        return user;
    }

    @Override
    public void deleteByUsername(String username) {
        User user = findByUsername(username);
        if (user != null) {
            getCurrentSession().delete(user);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        Query<Long> query = getCurrentSession().createQuery(
            "select count(*) from User where username = :username",
            Long.class
        );
        query.setParameter("username", username);
        return query.uniqueResult() > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        Query<Long> query = getCurrentSession().createQuery(
            "select count(*) from User where email = :email",
            Long.class
        );
        query.setParameter("email", email);
        return query.uniqueResult() > 0;
    }
} 