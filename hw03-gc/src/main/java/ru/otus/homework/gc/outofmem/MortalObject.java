package ru.otus.homework.gc.outofmem;

public class MortalObject {
    final private Integer number;
    private Object obj;
    public MortalObject(Integer number) {
        this.number = number;
        obj = this;
    }
}
