package ru.otus.homework.gc.outofmem;

import java.time.LocalTime;

public class ImmortalObject {
    final private Integer number;
    final public LocalTime createTime;

    public ImmortalObject(Integer number) {
        this.number = number;
        createTime = LocalTime.now();
    }
}
