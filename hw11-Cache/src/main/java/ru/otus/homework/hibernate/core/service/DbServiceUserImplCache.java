package ru.otus.homework.hibernate.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.homework.cashe.cachehw.HwCache;
import ru.otus.homework.cashe.cachehw.HwListener;
import ru.otus.homework.hibernate.core.dao.UserDao;
import ru.otus.homework.hibernate.core.model.User;
import ru.otus.homework.hibernate.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImplCache implements DBServiceUser {
  private static Logger logger = LoggerFactory.getLogger(DbServiceUserImplCache.class);
  private final UserDao userDao;
  private  HwCache<Long, User> cache = null;

  public DbServiceUserImplCache(UserDao userDao) {
    this.userDao = userDao;
  }

  public DbServiceUserImplCache(UserDao userDao, HwCache<Long, User> cache) {
    this.userDao = userDao;
    this.cache = cache;
    HwListener<Long, User> listener = new HwListener<>() {
      @Override
      public void notify(Long key, User value, String action) {
        logger.info("key:{}, value:{}, action: {}", key, value, action);
      }
    };
    cache.addListener(listener);
  }

  @Override
  public long saveUser(User user) {
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        long userId = userDao.saveUser(user);
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
    if (cache != null) {
      User cachedUser = cache.get(id);
      if (cachedUser != null) return Optional.of(cachedUser);
    }
    try (SessionManager sessionManager = userDao.getSessionManager()) {
      sessionManager.beginSession();
      try {
        Optional<User> userOptional = userDao.findById(id);
        logger.info("user: {}", userOptional.orElse(null));
        if (userOptional.isPresent()) putInCache(userOptional.get());
        return userOptional;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        sessionManager.rollbackSession();
      }
      return Optional.empty();
    }
  }

  private void putInCache(User user) throws InterruptedException {
    if (cache != null) cache.put(user.getId(), user);
  }
}
