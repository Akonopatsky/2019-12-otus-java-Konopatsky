package ru.otus.homework.hibernate.core.service;

import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.hibernate.core.dao.UserDao;
import ru.otus.homework.hibernate.core.model.Address;
import ru.otus.homework.hibernate.core.model.Phone;
import ru.otus.homework.hibernate.core.model.User;
import ru.otus.homework.hibernate.hibernate.HibernateUtils;
import ru.otus.homework.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.homework.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DbServiceUserImplTest {
    private static SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(
            "hibernate.cfg.xml", User.class, Address.class, Phone.class);
    private SessionManagerHibernate sessionManager;
    private UserDao userDao;
    private DbServiceUserImpl dbServiceUser;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManager);
        dbServiceUser = new DbServiceUserImpl(userDao);
    }

    @Test
    @DisplayName("Проверка отсутвия апдейтов при сохранении")
    void saveAndLoadUser() {
        User user1 = new User("First", 21);
        user1.setAddress("Московсое шоссе 1");
        user1.addPhone("11111111");
        user1.addPhone("22222222");
        dbServiceUser.saveUser(user1);
        assertThat(getUserStatistics().getUpdateCount()).isEqualTo(0);
        Optional<User> expectedUser = dbServiceUser.getUser(user1.getId());
        assertEquals(user1, expectedUser.get());
    }

    protected EntityStatistics getUserStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getEntityStatistics(User.class.getName());
    }
}