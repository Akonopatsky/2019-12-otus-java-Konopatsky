package ru.otus.hw12.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw12.dataaccsess.core.model.Address;
import ru.otus.hw12.dataaccsess.core.model.Phone;
import ru.otus.hw12.dataaccsess.core.model.User;
import ru.otus.hw12.dataaccsess.core.service.DbServiceUserImplCache;
import ru.otus.hw12.dataaccsess.hibernate.HibernateUtils;
import ru.otus.hw12.dataaccsess.hibernate.dao.UserDaoHibernate;
import ru.otus.hw12.dataaccsess.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DBServiceUserTest {
    private final Logger logger = LoggerFactory.getLogger(DBServiceUserTest.class);
    private final int DB_SIZE = 50;
    private static SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManager;
    private DBServiceUser DBServiceUser;

    @BeforeEach
    void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml", User.class, Address.class, Phone.class);
        sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDaoHibernate userDaoHibernate = new UserDaoHibernate(sessionManager);
        DBServiceUser = new DbServiceUserImplCache(userDaoHibernate);
    }

    @Test
    void getAllUsers() {
        insertUsers(DBServiceUser);
        List<User> allUsers = DBServiceUser.getAllUsers();
        for (int i = 0; i < DB_SIZE; i++) {
            User user = new User("user" + i, 25, "street" + i, "phone" + i);
            assertEquals(allUsers.get(i), user);
        }
    }

    private void insertUsers(DBServiceUser DBServiceUser) {
        for (int i = 0; i < DB_SIZE; i++) {
            User user = new User("user" + i, 25, "street" + i, "phone" + i);
            DBServiceUser.saveUser(user);
        }
    }
}