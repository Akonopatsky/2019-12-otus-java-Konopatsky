package ru.otus.homework.diyjson;

public interface DiyJsonWriter {

    String toJson(Object obj) throws UnsupportedTypeException;
}

