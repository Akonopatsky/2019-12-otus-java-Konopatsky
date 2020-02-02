package ru.otus.homework.gc.outofmem;

public class ImmortalObject {
    final private Integer number;
    private Object obj;

    public ImmortalObject(Integer number) {
        this.number = number;
    }
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
        obj = this;
    }
}
