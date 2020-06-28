package ru.otus.hw12.dao.hibernate.core.sessionmanager;

public interface SessionManager extends AutoCloseable {
    void beginSession();
    void commitSession();
    void rollbackSession();
    void close();

    DatabaseSession getCurrentSession();
}
