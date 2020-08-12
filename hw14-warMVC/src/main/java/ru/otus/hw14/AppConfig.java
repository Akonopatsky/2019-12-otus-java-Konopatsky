package ru.otus.hw14;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw14.dataaccsess.core.model.Address;
import ru.otus.hw14.dataaccsess.core.model.Phone;
import ru.otus.hw14.dataaccsess.core.model.User;
import ru.otus.hw14.dataaccsess.hibernate.HibernateUtils;

@Configuration
public class AppConfig {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(
                "hibernate.cfg.xml", User.class, Address.class, Phone.class);
    }

}
