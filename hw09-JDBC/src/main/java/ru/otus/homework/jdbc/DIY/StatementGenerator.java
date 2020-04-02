package ru.otus.homework.jdbc.DIY;

import ru.otus.homework.jdbc.mapper.UnsupportedTypeException;

import java.sql.ResultSet;

public interface StatementGenerator<T> {
    String getInsertStatement();
    String getUpdateStatement();
    String getSelectStatement();
    T createObject(ResultSet resultSet) throws UnsupportedTypeException;
}
