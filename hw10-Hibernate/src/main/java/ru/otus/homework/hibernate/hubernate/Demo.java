package ru.otus.homework.hibernate.hubernate;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.CriteriaImpl;
import ru.otus.homework.hibernate.core.dao.UserDao;
import ru.otus.homework.hibernate.core.model.AddressDataSet;
import ru.otus.homework.hibernate.core.model.PhoneDataSet;
import ru.otus.homework.hibernate.core.model.User;
import ru.otus.homework.hibernate.core.service.DBServiceUser;
import ru.otus.homework.hibernate.core.service.DbServiceUserImpl;
import ru.otus.homework.hibernate.hubernate.dao.UserDaoHibernate;
import ru.otus.homework.hibernate.hubernate.sessionmanager.DatabaseSessionHibernate;
import ru.otus.homework.hibernate.hubernate.sessionmanager.SessionManagerHibernate;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class Demo {
    private static SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml"
            , User.class, AddressDataSet.class, PhoneDataSet.class);

    public static void main(String[] args) {
        SessionManagerHibernate sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManagerHibernate);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
        User user1 = new User("First", 21);
        User user2 = new User("Second", 22);
        User user3 = new User("Therd", 23);
        dbServiceUser.saveUser(user1);
        dbServiceUser.saveUser(user2);
        dbServiceUser.saveUser(user3);
        Optional<User> loadUser1 = dbServiceUser.getUser(1);
        Optional<User> loadUser2 = dbServiceUser.getUser(2);
        Optional<User> loadUser3 = dbServiceUser.getUser(3);
        System.out.println(loadUser2);
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user3);
            User user4 = session.get(User.class, 3L);
/*            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rootEntry = cq.from(User.class);
            CriteriaQuery<User> all = cq.select(rootEntry);
            TypedQuery<User> allQuery = session.createQuery(all);
            System.out.println(allQuery);*/
            List<User> users = session.createQuery("from User", User.class).getResultList();
            for (User user : users) {
                System.out.println(user);
            }
        }

    }
}
