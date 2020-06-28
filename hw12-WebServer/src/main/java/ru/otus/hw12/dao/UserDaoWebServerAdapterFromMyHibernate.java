package ru.otus.hw12.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12.dao.hibernate.core.model.User;
import ru.otus.hw12.dao.hibernate.core.service.DbServiceException;
import ru.otus.hw12.dao.hibernate.core.sessionmanager.SessionManager;
import ru.otus.hw12.dao.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.hw12.dao.hibernate.hibernate.sessionmanager.DatabaseSessionHibernate;

import java.util.List;
import java.util.Optional;

public class UserDaoWebServerAdapterFromMyHibernate implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(UserDaoWebServerAdapterFromMyHibernate.class);
    private final UserDaoHibernate userDaoHibernate;

    public UserDaoWebServerAdapterFromMyHibernate(UserDaoHibernate userDaoHibernate) {
        this.userDaoHibernate = userDaoHibernate;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDaoHibernate.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDaoHibernate.saveUser(user);
                sessionManager.commitSession();
                logger.info("created user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        try (SessionManager sessionManager = userDaoHibernate.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDaoHibernate.findById(id);
                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (SessionManager sessionManager = userDaoHibernate.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return getAllUsersFromHibernate();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }

    public List<User> getAllUsersFromHibernate() {
        DatabaseSessionHibernate currentSession = (DatabaseSessionHibernate) userDaoHibernate.getSessionManager().getCurrentSession();
        try {
            return currentSession.getHibernateSession().createQuery("SELECT a FROM User a", User.class).getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
