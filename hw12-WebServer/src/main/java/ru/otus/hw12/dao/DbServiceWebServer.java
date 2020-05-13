package ru.otus.hw12.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12.hibernate.core.model.User;
import ru.otus.hw12.hibernate.core.service.DbServiceException;
import ru.otus.hw12.hibernate.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public class DbServiceWebServer implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(DbServiceWebServer.class);
    private final UserDaoWebServer userDao;

    public DbServiceWebServer(UserDaoWebServer userDao) {
        this.userDao = userDao;
    }

    @Override
    public long saveUser(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.saveUser(user);
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
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);
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
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return userDao.getAllUsers();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }
}
