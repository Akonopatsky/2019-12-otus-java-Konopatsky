package ru.otus.homework.gc.outofmem;

public class MemoryFillObject {
    static private long quantity = 0;
    final private long number;
    final private long createTime;
    public Object[] objects;

    public static long getQuantity() {
        return quantity;
    }

    public long getNumber() {
        return number;
    }

    public long getCreateTime() {
        return createTime;
    }

    public MemoryFillObject(Object... objects) {
        quantity++;
        number = quantity;
        createTime = System.currentTimeMillis();
        this.objects = objects;
    }
}
