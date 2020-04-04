package ru.otus.homework.jdbc.jdbc.dao;

import ru.otus.homework.jdbc.DIY.GenericDao;
import ru.otus.homework.jdbc.DIY.JdbcGenerator;
import ru.otus.homework.jdbc.core.dao.DaoInterface;
import ru.otus.homework.jdbc.core.model.Account;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.jdbc.DbExecutor;
import ru.otus.homework.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.Optional;

public class DiyAccountDaoJdbc extends GenericDao<Account> implements DaoInterface<Account> {
    public DiyAccountDaoJdbc(SessionManagerJdbc sessionManager, DbExecutor<Account> dbExecutor, JdbcGenerator jdbcGenerator) {
        super(sessionManager, dbExecutor, jdbcGenerator);
    }

    @Override
    public Optional findById(long id) {
        return super.findById(id);
    }

    @Override
    public long save(Account account) {
        return super.save(account);
    }

    @Override
    public long update(Account account) {
        return super.update(account);
    }

    @Override
    public SessionManager getSessionManager() {
        return super.getSessionManager();
    }
}
