package ru.otus.hw12.dao.hibernate.core.service;

import ru.otus.hw12.dao.hibernate.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);

}
