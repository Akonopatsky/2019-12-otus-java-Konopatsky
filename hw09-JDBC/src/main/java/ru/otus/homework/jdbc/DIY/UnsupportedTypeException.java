package ru.otus.homework.jdbc.DIY;

public class UnsupportedTypeException extends Exception {
    public UnsupportedTypeException(String s) {
        super(s);
    }

    public UnsupportedTypeException(Exception e) {
        super(e);
    }
}
