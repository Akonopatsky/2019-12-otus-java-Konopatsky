package ru.otus.hw12.hibernate.core.service;

import ru.otus.hw12.hibernate.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);

}
