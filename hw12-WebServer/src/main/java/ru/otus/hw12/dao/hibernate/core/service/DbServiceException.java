package ru.otus.hw12.dao.hibernate.core.service;

public class DbServiceException extends RuntimeException {
  public DbServiceException(Exception e) {
    super(e);
  }
}
