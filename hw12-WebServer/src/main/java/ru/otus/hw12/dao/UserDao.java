package ru.otus.hw12.dao;

import ru.otus.hw12.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);
    Optional<User> findRandomUser();
    Optional<User> findByLogin(String login);
}