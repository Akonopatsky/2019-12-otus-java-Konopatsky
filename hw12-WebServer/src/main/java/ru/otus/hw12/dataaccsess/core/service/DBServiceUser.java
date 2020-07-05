package ru.otus.hw12.dataaccsess.core.service;

import ru.otus.hw12.dataaccsess.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {
    long saveUser(User user);

    Optional<User> getUser(long id);

    List<User> getAllUsers();
}
