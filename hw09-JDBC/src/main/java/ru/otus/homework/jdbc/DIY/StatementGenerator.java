package ru.otus.homework.jdbc.DIY;

import java.sql.ResultSet;

public interface StatementGenerator<T> {
    String getInsertStatement();
    String getUpdateStatement();
    String getSelectStatement();
    T createObject(ResultSet resultSet) throws UnsupportedTypeException;
}
