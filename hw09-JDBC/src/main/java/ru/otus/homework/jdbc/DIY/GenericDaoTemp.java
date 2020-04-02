package ru.otus.homework.jdbc.DIY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.core.dao.UserDao;
import ru.otus.homework.jdbc.core.dao.UserDaoException;
import ru.otus.homework.jdbc.core.model.User;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.jdbc.DbExecutor;
import ru.otus.homework.jdbc.jdbc.sessionmanager.SessionManagerJdbc;
import ru.otus.homework.jdbc.mapper.UnsupportedTypeException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class GenericDaoTemp implements UserDao {
    private static Logger logger = LoggerFactory.getLogger(GenericDaoTemp.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<User> dbExecutor;
    private final JdbcGenerator<User> jdbcGenerator;

    public GenericDaoTemp(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor, JdbcGenerator jdbcGenerator) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.jdbcGenerator = jdbcGenerator;
    }

    @Override
    public Optional<User> findById(long id) {
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

    @Override
    public long saveUser(Object user) {
        return 0;
    }

    public long saveUser(User user) {
        try {
            return dbExecutor.insertRecord(getConnection(), jdbcGenerator.getInsertStatement(), jdbcGenerator.getValues(user));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
