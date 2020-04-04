package ru.otus.homework.jdbc.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.jdbc.core.dao.DaoInterface;
import ru.otus.homework.jdbc.core.model.User;
import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;


import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

  private final DaoInterface daoInterface;

  public DbServiceUserImpl(DaoInterface daoInterface) {
    this.daoInterface = daoInterface;
  }

  @Override
  public long saveUser(User user) {
    try (SessionManager sessionManager = daoInterface.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = daoInterface.save(user);
        sessionManager.commitSession();

        logger.info("created user: {}", userId);
        return userId;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
        throw new DbServiceException(e);
      }
    }
  }

  @Override
  public Optional<User> getUser(long id) {
    try (SessionManager sessionManager = daoInterface.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = daoInterface.findById(id);

        logger.info("user: {}", userOptional.orElse(null));
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

}
