package ru.otus.hw12.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12.hibernate.core.model.Address;
import ru.otus.hw12.hibernate.core.model.Phone;
import ru.otus.hw12.hibernate.core.model.User;
import ru.otus.hw12.hibernate.hibernate.HibernateUtils;
import ru.otus.hw12.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.hw12.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DbServiceWebServerTest {
    private final Logger logger = LoggerFactory.getLogger(UserDaoAdapter.class);
    private final int DB_SIZE = 50;
    private static SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManager;
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml", User.class, Address.class, Phone.class);
        sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDaoHibernate userDaoHibernate = new UserDaoHibernate(sessionManager);
        UserDaoAdapter userDaoAdapter = new UserDaoAdapter(userDaoHibernate);
        userDao = new DbServiceWebServer(userDaoAdapter);
    }

    @Test
    void getAllUsers() {
        insertusers(userDao);
        List<User> allUsers = userDao.getAllUsers();
        for (int i = 0; i < DB_SIZE; i++) {
            User user = new User("user" + i, 25, "street" + i, "phone" + i);
            assertEquals(allUsers.get(i), user);
        }
    }

    private void insertusers(UserDao userDao) {
        for (int i = 0; i < DB_SIZE; i++) {
            User user = new User("user" + i, 25, "street" + i, "phone" + i);
            userDao.saveUser(user);
        }
    }
}