package ru.otus.hw12.dataaccsess.core.dao;


import ru.otus.hw12.dataaccsess.core.model.User;
import ru.otus.hw12.dataaccsess.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);

    long saveUser(User user);

    List<User> getAllUsers();

    SessionManager getSessionManager();
}
