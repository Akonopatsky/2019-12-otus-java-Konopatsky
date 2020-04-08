package ru.otus.homework.hibernate.core.service;



import ru.otus.homework.hibernate.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

  long saveUser(User user);

  Optional<User> getUser(long id);

}
