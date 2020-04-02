package ru.otus.homework.jdbc.DIY;

import ru.otus.homework.jdbc.mapper.UnsupportedTypeException;

public interface Mapper<T> {
    void create(T objectData) throws UnsupportedTypeException;
    void update(T objectData) throws UnsupportedTypeException;
    void createOrUpdate(T objectData) throws UnsupportedTypeException;
    <T> T load(long id, Class<T> clazz);
}
