package ru.otus.homework.jdbc.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.jdbc.mapper.UnsupportedTypeException;
import ru.otus.homework.jdbc.core.dao.DaoException;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.jdbc.DbExecutor;
import ru.otus.homework.jdbc.jdbc.mapper.StatementConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class GenericDao<T> {
    private static Logger logger = LoggerFactory.getLogger(GenericDao.class);

    private final SessionManager sessionManager;
    private final DbExecutor<T> dbExecutor;
    private final StatementConstructor<T> jdbcGenerator;

    public GenericDao(SessionManager sessionManager, DbExecutor<T> dbExecutor, StatementConstructor<T> jdbcGenerator) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.jdbcGenerator = jdbcGenerator;
    }

    public Optional<T> findById(long id) {
        try {
            return dbExecutor.selectRecord(getConnection(), jdbcGenerator.getSelectStatement(), id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return jdbcGenerator.createObject(resultSet);
                    }
                } catch (SQLException | UnsupportedTypeException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    public long save(T object) {
        try {
            return dbExecutor.insertRecord(getConnection(), jdbcGenerator.getInsertStatement(), jdbcGenerator.getValuesForSave(object));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    public long update(T object) {
        try {
            return dbExecutor.updateRecord(getConnection(), jdbcGenerator.getUpdateStatement(), jdbcGenerator.getValuesForUpdate(object));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e);
        }
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
