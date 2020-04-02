package ru.otus.homework.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.DIY.Mapper;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.jdbc.DbExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcMapper<T> implements Mapper<T> {
    final private Logger logger = LoggerFactory.getLogger(JdbcMapper.class);
    final private SessionManager sessionManager;
    final private DbExecutor<T> dbExecutor;
    final private Tamplater<T> tamplater;

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    public JdbcMapper(SessionManager sessionManager, DbExecutor<T> dbExecutor, T objectData) throws UnsupportedTypeException {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        tamplater = new Tamplater(objectData);
    }

    @Override
    public void create(T objectData) throws UnsupportedTypeException {
        try {
            dbExecutor.insertRecord(getConnection(), tamplater.getInsertSQLString(), tamplater.getValues(objectData));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UnsupportedTypeException(e);
        }
    }

    @Override
    public void update(T objectData) throws UnsupportedTypeException {

    }
    @Override
    public void createOrUpdate(T objectData) throws UnsupportedTypeException {

    }

    @Override
    public <T> T load(long id, Class<T> clazz) {
/*        try {
            return  dbExecutor.selectRecord(getConnection(), tamplater.getSelectSQLString(), id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return tamplater.createObject(resultSet, clazz);
                    }
                } catch (SQLException | UnsupportedTypeException e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }*/
        return (T) Optional.empty();
    }

}
