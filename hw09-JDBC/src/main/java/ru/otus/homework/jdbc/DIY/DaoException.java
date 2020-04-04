package ru.otus.homework.jdbc.DIY;

public class DaoException extends RuntimeException {
    public DaoException(Exception e) {
        super(e);
    }
}
