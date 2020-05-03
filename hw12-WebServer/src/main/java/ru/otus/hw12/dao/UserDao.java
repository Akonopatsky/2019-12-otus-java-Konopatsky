package ru.otus.hw12.dao;


import ru.otus.hw12.hibernate.core.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    long saveUser(User user);

    List<User> getAllUsers();
}