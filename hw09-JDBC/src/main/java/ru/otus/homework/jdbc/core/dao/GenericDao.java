package ru.otus.homework.jdbc.core.dao;

import ru.otus.homework.jdbc.core.sessionmanager.SessionManager;

import java.util.Optional;

public interface GenericDao<T> {

        Optional findById(long id);

        long save(T entity);

        SessionManager getSessionManager();
}
