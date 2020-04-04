package ru.otus.homework.jdbc.jdbc.dao;

import ru.otus.homework.jdbc.jdbc.mapper.StatementConstructor;
import ru.otus.homework.jdbc.core.dao.DaoInterface;
import ru.otus.homework.jdbc.core.model.User;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.jdbc.DbExecutor;

import java.util.Optional;

public class UserDaoJdbc extends GenericDao<User> implements DaoInterface<User> {

    public UserDaoJdbc(SessionManager sessionManager, DbExecutor<User> dbExecutor, StatementConstructor<User> jdbcGenerator) {
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
