package ru.otus.homework.jdbc.core.sessionmanager;

import java.sql.Connection;

public interface DatabaseSession {
    Connection getConnection();
}
