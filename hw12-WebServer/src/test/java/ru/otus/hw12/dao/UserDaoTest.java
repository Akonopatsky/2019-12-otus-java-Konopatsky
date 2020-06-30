package ru.otus.hw12.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw12.dao.hibernate.core.model.Address;
import ru.otus.hw12.dao.hibernate.core.model.Phone;
import ru.otus.hw12.dao.hibernate.core.model.User;
import ru.otus.hw12.dao.hibernate.core.service.DbServiceUserImplCache;
import ru.otus.hw12.dao.hibernate.hibernate.HibernateUtils;
import ru.otus.hw12.dao.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.hw12.dao.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDaoTest {
    private final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);
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
        userDao = new DbServiceUserImplCache(userDaoHibernate);
    }

    @Test
    void getAllUsers() {
        insertUsers(userDao);
        List<User> allUsers = userDao.getAllUsers();
        for (int i = 0; i < DB_SIZE; i++) {
            User user = new User("user" + i, 25, "street" + i, "phone" + i);
            assertEquals(allUsers.get(i), user);
        }
    }

    private void insertUsers(UserDao userDao) {
        for (int i = 0; i < DB_SIZE; i++) {
            User user = new User("user" + i, 25, "street" + i, "phone" + i);
            userDao.saveUser(user);
        }
    }
}