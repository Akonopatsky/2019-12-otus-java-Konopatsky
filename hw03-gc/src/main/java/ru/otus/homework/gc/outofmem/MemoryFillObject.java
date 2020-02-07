package ru.otus.homework.gc.outofmem;

import java.time.LocalTime;

public class MemoryFillObject {
    final private Integer number;
    final private LocalTime createTime;
    public Object[] objects;
    public LocalTime getCreateTime() {
        return createTime;
    }
    public MemoryFillObject(Integer number, Object... objects) {
        this.number = number;
        createTime = LocalTime.now();
        this.objects = objects;
    }
}
