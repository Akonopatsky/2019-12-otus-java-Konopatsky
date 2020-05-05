package ru.otus.hw12.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12.hibernate.core.model.User;
import ru.otus.hw12.hibernate.core.sessionmanager.SessionManager;
import ru.otus.hw12.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.hw12.hibernate.hibernate.sessionmanager.DatabaseSessionHibernate;

import java.util.List;
import java.util.Optional;

public class UserDaoAdapter implements UserDaoWebServer {
    private final Logger logger = LoggerFactory.getLogger(UserDaoAdapter.class);
    private final UserDaoHibernate userDaoHibernate;

    public UserDaoAdapter(UserDaoHibernate userDaoHibernate) {
        this.userDaoHibernate = userDaoHibernate;
    }


    @Override
    public Optional<User> findById(long id) {
        return userDaoHibernate.findById(id);
    }

    @Override
    public long saveUser(User user) {
        return userDaoHibernate.saveUser(user);
    }

    @Override
    public SessionManager getSessionManager() {
        return userDaoHibernate.getSessionManager();
    }

    @Override
    public List<User> getAllUsers() {
        DatabaseSessionHibernate currentSession = (DatabaseSessionHibernate) userDaoHibernate.getSessionManager().getCurrentSession();
        try {
            return currentSession.getHibernateSession().createQuery("SELECT a FROM User a", User.class).getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
