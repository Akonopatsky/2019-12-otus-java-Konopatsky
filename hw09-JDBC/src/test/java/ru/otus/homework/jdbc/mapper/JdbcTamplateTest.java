package ru.otus.homework.jdbc.mapper;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.core.dao.DaoInterface;
import ru.otus.homework.jdbc.core.model.Account;
import ru.otus.homework.jdbc.core.model.User;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.h2.DataSourceH2;
import ru.otus.homework.jdbc.h2.TableFactory;
import ru.otus.homework.jdbc.jdbc.DbExecutor;
import ru.otus.homework.jdbc.jdbc.dao.AccountDaoJdbc;
import ru.otus.homework.jdbc.jdbc.dao.UserDaoJdbc;
import ru.otus.homework.jdbc.jdbc.mapper.StatementConstructor;
import ru.otus.homework.jdbc.jdbc.mapper.JdbcTamplate;
import ru.otus.homework.jdbc.jdbc.mapper.UnsupportedTypeException;
import ru.otus.homework.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class JdbcTamplateTest {
    private static TableFactory tableFactory;
    private static DataSource dataSource;
    private Logger logger = LoggerFactory.getLogger(JdbcTamplateTest.class);
    private SessionManager sessionManager;
    private DbExecutor<User> userDbExecutor = new DbExecutor<>();
    private DbExecutor<Account> accountDbExecutor = new DbExecutor<>();
    private DaoInterface<User> userDao;
    private DaoInterface<Account>  accountDao;
    private JdbcTamplate<User> userJdbcTamplate;
    private JdbcTamplate<Account> accountJdbcTamplate;
    private User user;
    private Account account;

    @BeforeAll
    static void beforeAll() throws SQLException {
        dataSource = new DataSourceH2();
        tableFactory = new TableFactory(dataSource);
        tableFactory.createTable(new User());
        tableFactory.createTable(new Account());
    }

    @BeforeEach
    void setUp() throws UnsupportedTypeException {
        user = new User(1, "First user", 34);
        account = new Account(1, "first account", new BigDecimal(17));
        sessionManager = new SessionManagerJdbc(dataSource);
        StatementConstructor<User> userJdbcGenerator = new StatementConstructor<>(new User());
        StatementConstructor<Account> accountJdbcGenerator = new StatementConstructor<>(new Account());
        userDao = new UserDaoJdbc(sessionManager, userDbExecutor, userJdbcGenerator);
        accountDao = new AccountDaoJdbc(sessionManager, accountDbExecutor, accountJdbcGenerator);
        userJdbcTamplate = new JdbcTamplate<>(userDao);
        accountJdbcTamplate = new JdbcTamplate<>(accountDao);
    }

    @Test
    @DisplayName("create and load ")
    void create() {
        userJdbcTamplate.create(user);
        accountJdbcTamplate.create(account);
        User redUser = userJdbcTamplate.load(1, User.class);
        Account redAccount = accountJdbcTamplate.load(17, Account.class);
        assertEquals(user, redUser);
        assertEquals(account, redAccount);
    }

    @Test
    void update() {
        accountJdbcTamplate.create(new Account(3,"old", new BigDecimal(13)));
        accountJdbcTamplate.create(new Account(4,"temp", new BigDecimal(14)));
        accountJdbcTamplate.update(new Account(1, "updated", new BigDecimal(13)));
        Account redAcc = accountJdbcTamplate.load(13, Account.class);
        assertEquals(redAcc.type, "updated");
    }
}