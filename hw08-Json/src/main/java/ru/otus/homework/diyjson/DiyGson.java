package ru.otus.homework.diyjson;

import com.google.gson.Gson;

import javax.json.Json;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class DiyGson {
    public String toJson(Object obj) {
        Class<?> clazz = obj.getClass();

        if (clazz.isPrimitive()) {
            System.out.println("примитив");
        }
        else {

        }
        if (obj instanceof AbstractCollection) {
            System.out.println(clazz.getCanonicalName()+" collections");


        }
        if (clazz.isArray()) {
            System.out.println(clazz.getCanonicalName()+" array");
            System.out.println(clazz.getComponentType());
        }

        Field[] fields = clazz.getFields();
        System.out.println(clazz.getCanonicalName());
        for (Field field : fields) {
            System.out.println(field);
        }
        return null;
    }

    public static void main(String[] args) {
        String[] s = {"aa","bb"};
        List<String> list = Arrays.asList("dd","zz","dhhh");
        BagOfPrimitives bag = new BagOfPrimitives(22, "test", 10);
        DiyGson diyGson = new DiyGson();
        Gson gson = new Gson();
        Map<String,String[]> map = new HashMap<>();
        map.put("mapkey",s);
/*        diyGson.toJson(s);
        diyGson.toJson(list);*/
        Object[] arrayOfObj = new Object[5];
        arrayOfObj[0] = list;
        arrayOfObj[1] = s;
        arrayOfObj[2] = bag;
        arrayOfObj[3] = map;
        Object[] arr2 = new Object[2];
        arr2[0] = arrayOfObj;
        arrayOfObj[4] = arr2;
        System.out.println(gson.toJson(arrayOfObj));
        System.out.println(gson.toJson(map));
        System.out.println(gson.toJson(list));

    }
}
