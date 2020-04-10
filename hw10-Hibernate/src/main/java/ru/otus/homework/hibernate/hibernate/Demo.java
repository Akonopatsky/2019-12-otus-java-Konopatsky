package ru.otus.homework.hibernate.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.homework.hibernate.core.dao.UserDao;
import ru.otus.homework.hibernate.core.model.AddressDataSet;
import ru.otus.homework.hibernate.core.model.PhoneDataSet;
import ru.otus.homework.hibernate.core.model.User;
import ru.otus.homework.hibernate.core.service.DBServiceUser;
import ru.otus.homework.hibernate.core.service.DbServiceUserImpl;
import ru.otus.homework.hibernate.hibernate.dao.UserDaoHibernate;
import ru.otus.homework.hibernate.hibernate.sessionmanager.SessionManagerHibernate;

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
        user1.setAddress("Bruklin");
        user1.addPhones("3333");
        User user2 = new User("Second", 22);
        User user3 = new User("Therd", 23);
        dbServiceUser.saveUser(user1);
        dbServiceUser.saveUser(user2);
        dbServiceUser.saveUser(user3);
        Optional<User> loadUser1 = dbServiceUser.getUser(1);
        System.out.println("load " + loadUser1);
        user1.setAddress("Moscow");
        dbServiceUser.saveUser(user1);
        System.out.println("_____________________________");
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery("from User", User.class).getResultList();
            for (User user : users) {
                System.out.println(user);
            }
            List<AddressDataSet> addresses = session.createQuery("from AddressDataSet", AddressDataSet.class).getResultList();
            for (AddressDataSet address : addresses) {
                System.out.println(address.toString());
            }
        }

    }
}
