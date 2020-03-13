package ru.otus.homework.diyjson.temp;

import com.google.gson.Gson;

import java.util.ArrayList;


public class GsonDemo {
    public static void main(String[] args) {
        Gson gson = new Gson();
        ArrayList obj = new ArrayList<Integer>();
        obj.add(3);
        obj.add(4);
//        obj.add(new BagOfPrimitives(22, "test", 10));
        obj.add(5);
        System.out.println(obj);

        String json = gson.toJson(obj);
        System.out.println(json);

        ArrayList<Integer> obj2 = gson.fromJson(json, ArrayList.class);
        System.out.println(obj.equals(obj2));
        System.out.println(obj2);
    }
}
