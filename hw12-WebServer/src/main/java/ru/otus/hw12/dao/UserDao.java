package ru.otus.hw12.dao;

import ru.otus.hw12.hibernate.core.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getAllUsers();
}
