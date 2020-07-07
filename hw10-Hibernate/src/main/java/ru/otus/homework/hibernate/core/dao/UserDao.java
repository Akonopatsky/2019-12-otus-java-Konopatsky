package ru.otus.homework.hibernate.core.dao;


import ru.otus.homework.hibernate.core.model.User;
import ru.otus.homework.hibernate.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long saveUser(User user);

    SessionManager getSessionManager();
}
