package ru.otus.homework.gc.outofmem;

import java.util.ArrayList;

public class OutOfMemGC {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<ImmortalObject> list = new ArrayList<>();
        int sleepStep = 10000;
        int sleepTime = 200;
        long count = 0;

        while (true) {
            for (int i = 0; i < sleepStep; i++) {
                list.add(new ImmortalObject(Integer.valueOf(i)));
            }
            for (int i = 0; i < sleepStep/2; i++) {
                list.remove(list.size()-1);
            }
            count++;
            System.out.print(count+" > ");
            System.out.println(list.get(list.size()-1).createTime);
            Thread.sleep(sleepTime);
        }

    }
}
