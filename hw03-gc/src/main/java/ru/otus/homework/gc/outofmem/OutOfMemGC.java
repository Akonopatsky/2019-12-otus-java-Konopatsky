package ru.otus.homework.gc.outofmem;

import java.util.ArrayList;

public class OutOfMemGC {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Object> list = new ArrayList<>();
        int max = Integer.MAX_VALUE;
        for (int i = 0; i < max; i++) {
            list.add(new MortalObject(i));
            list.add(new ImmortalObject(i));
            if (i%1000000 == 0) {
                System.out.println(i);
                Thread.sleep(1000);
            }
            list.remove(0);
            list.remove(0);
        }
    }
}
