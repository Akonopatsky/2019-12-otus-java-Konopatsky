package ru.otus.hw12.dao.hibernate.core.dao;

public class UserDaoException extends RuntimeException {
  public UserDaoException(Exception ex) {
    super(ex);
  }
}
