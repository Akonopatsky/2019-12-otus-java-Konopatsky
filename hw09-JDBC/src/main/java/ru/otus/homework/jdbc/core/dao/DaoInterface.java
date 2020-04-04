package ru.otus.homework.jdbc.core.dao;

import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface DaoInterface<T>{
  Optional findById(long id);

  long save(T object);

  long update(T object);

  SessionManager getSessionManager();
}
