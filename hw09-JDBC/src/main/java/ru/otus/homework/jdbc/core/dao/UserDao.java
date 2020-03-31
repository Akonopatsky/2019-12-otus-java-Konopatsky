package ru.otus.homework.jdbc.core.dao;

import ru.otus.homework.jdbc.core.model.User;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao extends GenericDao<User>{
  Optional findById(long id);

  long save(User user);

  SessionManager getSessionManager();
}
