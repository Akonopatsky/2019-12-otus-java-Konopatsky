package ru.otus.homework.jdbc.core.dao;

public class DaoException extends RuntimeException {
    public DaoException(Exception ex) {
        super(ex);
    }
}
