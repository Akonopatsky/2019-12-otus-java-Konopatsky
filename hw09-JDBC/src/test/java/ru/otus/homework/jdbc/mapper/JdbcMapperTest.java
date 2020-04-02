package ru.otus.homework.jdbc.mapper;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.DIY.Mapper;
import ru.otus.homework.jdbc.core.model.Account;
import ru.otus.homework.jdbc.core.model.User;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;
import ru.otus.homework.jdbc.h2.DataSourceH2;
import ru.otus.homework.jdbc.jdbc.DbExecutor;
import ru.otus.homework.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class JdbcMapperTest {
    private Logger logger = LoggerFactory.getLogger(JdbcMapperTest.class);
    private static DataSource dataSource;
    private SessionManager sessionManager;
    private DbExecutor<User> userDbExecutor = new DbExecutor<>();
    private DbExecutor<Account> accountDbExecutor = new DbExecutor<>();
    private User user;
    private Account account;

    @BeforeAll
    static void deforeAll() {
        dataSource = new DataSourceH2();
        try (PreparedStatement pst = dataSource.getConnection()
                .prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))"))
        {
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement pst = dataSource.getConnection().prepareStatement("create table Account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number )")) {
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        user = new User(1, "Mr First", 30);
        account = new Account(1111, "firstAcc", 1);
        sessionManager = new SessionManagerJdbc(dataSource);
        sessionManager.beginSession();
    }

    @Test
    void create() throws UnsupportedTypeException {
        System.out.println("test");
        Mapper<User> userMapper = new JdbcMapper<>(sessionManager, userDbExecutor, new User());
        Mapper<Account> accountMapper = new JdbcMapper<>(sessionManager, accountDbExecutor, new Account());
        userMapper.create(user);
        accountMapper.create(account);
        Tamplater<User> userTamplater = new Tamplater<>(new User());
        Tamplater<Account> accountTamplater = new Tamplater<>(new Account());
        Connection connection = sessionManager.getCurrentSession().getConnection();
        try (PreparedStatement pst = connection.prepareStatement(userTamplater.getSelectSQLString())) {
            pst.setLong(1, 1);
            System.out.println(pst.toString());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    logger.info(rs.getString(2));
                    logger.info(userTamplater.createObject(rs, User.class).toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



/*
        User user2 = userMapper.load(1, User.class);
        System.out.println(user2.toString());
*/



    }

    @Test
    void update() {
    }

    @Test
    void createOrUpdate() {
    }

    @Test
    void load() {
    }


    @AfterEach
    void tearDown() {
        sessionManager.commitSession();
    }
}