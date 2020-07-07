package ru.otus.homework.jdbc.core.service;


import ru.otus.homework.jdbc.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

}
