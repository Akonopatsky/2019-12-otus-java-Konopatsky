package ru.otus;

import java.util.ArrayList;

public class DIYarrayListDemo {
    public static void main(String[] args) {
        DIYarrayList<Integer> diYarrayList1 = new DIYarrayList<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            diYarrayList1.add(i);
        }
        int a = - 9999;
        int b  = (-1) & 0x7fffffff;

        System.out.println(b);
    }
}
