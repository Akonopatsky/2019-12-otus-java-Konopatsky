package ru.otus.homework.jdbc.DIY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.core.dao.DaoInterface;
import ru.otus.homework.jdbc.core.service.DbServiceException;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;

import java.lang.reflect.InvocationTargetException;


public class JdbcTamplate<T> {
    private static Logger logger = LoggerFactory.getLogger(JdbcTamplate.class);
    private final DaoInterface dao;

    public JdbcTamplate(DaoInterface dao) {
        this.dao = dao;
    }

    public void create(T objectData) throws UnsupportedTypeException {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long id = dao.save(objectData);
                sessionManager.commitSession();
                logger.info("created "+objectData.getClass().getSimpleName() + ": {}", id);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    public void update(T objectData) throws UnsupportedTypeException {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            dao.update(objectData);
        }
    }

    public void createOrUpdate(T objectData) throws UnsupportedTypeException {

    }

    public <T> T load(long id, Class<T> clazz) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                return clazz.getConstructor(clazz).newInstance(dao.findById(id).get());
            } catch (Exception e) {
                return null;
            }
        }
    }
}
