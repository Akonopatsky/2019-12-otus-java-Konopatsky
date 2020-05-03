package ru.otus.hw12.hibernate.core.dao;



import ru.otus.hw12.hibernate.core.model.User;
import ru.otus.hw12.hibernate.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {
  Optional<User> findById(long id);

  long saveUser(User user);

  SessionManager getSessionManager();
}
