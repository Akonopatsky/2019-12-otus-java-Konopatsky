package ru.otus.homework.jdbc.jdbc.dao;

import ru.otus.homework.jdbc.DIY.GenericDao;
import ru.otus.homework.jdbc.DIY.JdbcGenerator;
import ru.otus.homework.jdbc.core.dao.DaoInterface;
import ru.otus.homework.jdbc.core.model.Account;
import ru.otus.homework.jdbc.core.model.User;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.jdbc.DbExecutor;
import ru.otus.homework.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class DiyUserDaoJdbc extends GenericDao<User> implements DaoInterface<User> {

    public DiyUserDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<User> dbExecutor, JdbcGenerator jdbcGenerator) {
        super(sessionManager, dbExecutor, jdbcGenerator);
    }

    @Override
    public Optional findById(long id) {
        return super.findById(id);
    }

    @Override
    public long save(User user) {
        return super.save(user);
    }

    @Override
    public long update(User user) {
        return super.update(user);
    }

    @Override
    public SessionManager getSessionManager() {
        return super.getSessionManager();
    }
}
