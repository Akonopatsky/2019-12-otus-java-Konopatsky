package ru.otus.homework.jdbc.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.core.dao.DaoInterface;
import ru.otus.homework.jdbc.core.service.DbServiceException;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;

public class JdbcTamplate<T> implements Mapper<T> {
    private static Logger logger = LoggerFactory.getLogger(JdbcTamplate.class);
    private final DaoInterface<T> dao;

    public JdbcTamplate(DaoInterface<T> dao) {
        this.dao = dao;
    }

    @Override
    public void create(T objectData) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long id = dao.save(objectData);
                sessionManager.commitSession();
                logger.info("created " + objectData.getClass().getSimpleName() + ": {}", id);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void update(T objectData) {
        try (SessionManager sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            dao.update(objectData);
            sessionManager.commitSession();
        }
    }

    public void createOrUpdate(T objectData) {
        throw new UnsupportedOperationException();

    }

    @Override
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
