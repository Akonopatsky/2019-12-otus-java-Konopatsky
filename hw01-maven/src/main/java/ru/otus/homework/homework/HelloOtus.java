package ru.otus.homework.homework;

import com.google.common.collect.Lists;

import java.util.List;
public class HelloOtus {
    public static void main(String[] args) {
        System.out.println("HelloOtus ");
        List<Integer> list1 = Lists.newArrayList(1,2,3,4,5);
        System.out.println(list1);
        System.out.println(Lists.reverse(list1));

    }

}
