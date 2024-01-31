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
            session.beginTransaction();
            List<User> userList = session.createQuery("FROM User ", User.class).list();
            session.getTransaction().commit();
            return userList ;
        }

    }

    @Override
    public User findById(String id) {
        try (Session session = sessionFactoryConfig.getSession()) {
            session.beginTransaction();
            User user = session.get(User.class, UUID.fromString(id));
            session.getTransaction().commit();
            return user;
        }
    }

    @Override
    public boolean save(User user) {
        try (Session session = sessionFactoryConfig.getSession()) {
            session.beginTransaction();
            session.save(user);
            session.flush();
            session.getTransaction().commit();
            return true;
        }

    }

    @Override
    public User update(User user) {
        try (Session session = sessionFactoryConfig.getSession()) {
            session.beginTransaction();
            session.update(user);
            session.flush();
            session.getTransaction().commit();
            return user;
        }
    }

    @Override
    public User findByUsername(String username) {
        try (Session session = sessionFactoryConfig.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery(" FROM User u where u.username=:inputUsername");
            query.setParameter("inputUsername", username);
            User user =(User) query.uniqueResult();
            session.getTransaction().commit();
            return user;

        }
    }

    @Override
    public boolean isPresent(String username) {
        try (Session session = sessionFactoryConfig.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select count (*)  FROM User u where u.username=:inputUsername");
            query.setParameter("inputUsername", username);
            Long count = (Long) query.uniqueResult();
            session.getTransaction().commit();
            return count > 0;

        }

    }
}
