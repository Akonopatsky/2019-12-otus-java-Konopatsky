package ru.otus.homework;

import java.util.Collections;
import java.util.Comparator;

public class DIYarrayListDemo {
    public static void main(String[] args) {
        DIYarrayList<Integer> diYarrayList1 = new DIYarrayList<>();
        DIYarrayList<Integer> diYarrayList2 = new DIYarrayList<>();
        for (int i = 0; i < 20; i++) {
            diYarrayList1.add(i);
            diYarrayList2.add(30 - i);
        }
        Collections.addAll(diYarrayList1, 77, 78, -5);
        Collections.addAll(diYarrayList2, 0);
        Collections.copy(diYarrayList1, diYarrayList2);
        Collections.sort(diYarrayList1, Comparator.reverseOrder());
        for (Integer element : diYarrayList1) {
            System.out.print(element + " ");
        }
        System.out.println();
        for (Integer element : diYarrayList2) {
            System.out.print(element + " ");
        }
    }
}




