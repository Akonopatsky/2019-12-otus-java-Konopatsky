package ru.otus.homework.hibernate.core.service;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.cashe.cachehw.HwCache;
import ru.otus.homework.cashe.cachehw.MyCache;
import ru.otus.homework.hibernate.core.dao.UserDao;
import ru.otus.homework.hibernate.core.model.Address;
import ru.otus.homework.hibernate.core.model.Phone;
import ru.otus.homework.hibernate.core.model.User;
import ru.otus.homework.hibernate.hibernate.HibernateUtils;
import ru.otus.homework.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.homework.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DbServiceUserImplTest {
    private final Logger logger = LoggerFactory.getLogger(DbServiceUserImplTest.class);
    private final int DB_SIZE = 500000;
    private final int READ_COUNT = 100000;
    private static SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManager;
    private UserDao userDao;
    private DBServiceUser dbServiceSimple;
    private DBServiceUser dbServiceCached;

    @BeforeEach
    void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml", User.class, Address.class, Phone.class);
        sessionManager = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManager);
    }

    @Test
    @DisplayName("тест скорости")
    void dbWithCache() throws InterruptedException {

        HwCache cache = new MyCache();
        dbServiceSimple = new DbServiceUserImplCache(userDao);
        dbServiceCached = new DbServiceUserImplCache(userDao, cache);
        insertusers(dbServiceSimple);
        logger.info("время с кэшем: {}", speedTest(dbServiceCached));
        logger.info("время без кэша: {}", speedTest(dbServiceSimple));
        logger.info("время с кэшем: {}", speedTest(dbServiceCached));
        logger.info("время без кэша: {}", speedTest(dbServiceSimple));
        logger.info("время с кэшем: {}", speedTest(dbServiceCached));
        logger.info("время без кэша: {}", speedTest(dbServiceSimple));
        logger.info("время с кэшем: {}", speedTest(dbServiceCached));
        logger.info("время без кэша: {}", speedTest(dbServiceSimple));

        logger.info("время с кэшем до запуска GC: {}", speedTest(dbServiceCached));
        System.gc();
        Thread.sleep(100);
        logger.info("время с кэшем после GC: {}", speedTest(dbServiceCached));
    }

    private void insertusers(DBServiceUser dbService) {
        for (int i = 0; i < DB_SIZE; i++) {
            User user = new User("user" + i, 25, "street" + i, "phone" + i);
            dbService.saveUser(user);
        }
    }

    private void readusers(DBServiceUser dbService) {
        for (int i = 0; i < READ_COUNT; i++) {
            Optional<User> expectedUser = dbService.getUser(i + 1);
            assertEquals(new User("user" + i, 25, "street" + i, "phone" + i), expectedUser.get());
        }
        for (int i = READ_COUNT - 1; i >= 0; i--) {
            Optional<User> expectedUser = dbService.getUser(i + 1);
            assertEquals(new User("user" + i, 25, "street" + i, "phone" + i), expectedUser.get());
        }
    }

    private Long speedTest(DBServiceUser dbService) {
        long startTime;
        long time;
        startTime = System.currentTimeMillis();
        readusers(dbService);
        readusers(dbService);
        time = System.currentTimeMillis() - startTime;
        return time;
    }
}
