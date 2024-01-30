package ru.aston.homework.dao;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.aston.homework.config.SessionFactoryConfig;
import ru.aston.homework.entity.User;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepositoryImp implements UserRepo {


    private SessionFactoryConfig sessionFactoryConfig;

    public UserRepositoryImp(SessionFactoryConfig sessionFactoryConfig) {
        this.sessionFactoryConfig = sessionFactoryConfig;
    }

    @Override
    public List<User> index() {
        try (Session session = sessionFactoryConfig.getSession()) {
            return session.createQuery("FROM User ", User.class).list();
        }

    }

    @Override
    public User findById(String id) {
        try (Session session = sessionFactoryConfig.getSession()) {
            return session.get(User.class, UUID.fromString(id));
        }
    }

    @Override
    public boolean save(User user) {
        try (Session session = sessionFactoryConfig.getSession()) {
            session.beginTransaction();
            session.save(user);
            session.flush();
            return true;
        }

    }

    @Override
    public User update(User user) {
        try (Session session = sessionFactoryConfig.getSession()) {
            session.beginTransaction();
            session.update(user);
            session.flush();
            return user;
        }
    }

    @Override
    public User findByUsername(String username) {
        try (Session session = sessionFactoryConfig.getSession()) {
            Query query = session.createQuery(" FROM User u where u.username=:inputUsername");
            query.setParameter("inputUsername", username);

            return (User) query.uniqueResult();

        }
    }

    @Override
    public boolean isPresent(String username) {
        try (Session session = sessionFactoryConfig.getSession()) {
            Query query = session.createQuery("select count (*)  FROM User u where u.username=:inputUsername");
            query.setParameter("inputUsername", username);
            Long count = (Long) query.uniqueResult();
            return count > 0;

        }

    }
}
