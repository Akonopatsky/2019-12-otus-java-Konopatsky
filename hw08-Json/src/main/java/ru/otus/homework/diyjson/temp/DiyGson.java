package ru.otus.homework.diyjson.temp;

import com.google.gson.Gson;

import javax.json.Json;
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
        List<String> list = Arrays.asList("dd",null,"dhhh");
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
/*        System.out.println(gson.toJson(bag));
        System.out.println(gson.toJson(map));
        System.out.println(gson.toJson(list));


        var jsonObject = Json.createObjectBuilder()
                .add("firstName", "Duke")
                .add("age", 28)
                .add("streetAddress", "100 Internet Dr")
                .add("phoneNumbers",
                        Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("type", "home")
                                        .add("number", "222-222-2222")))
                .build();
        System.out.println(jsonObject);
        var jsonObject1 = Json.createObjectBuilder()
                .add("firstName", "Duke")
                .add("age", 28)
                .add("streetAddress", "100 Internet Dr")
                .add("phoneNumbers",
                        Json.createArrayBuilder()
                                .add(Json.createObjectBuilder()
                                        .add("type", "home")
                                        .add("number", "222-222-2222")))
                .add("array", Json.createArrayBuilder()
                .add(1)
                .add(2))
                .build();
        String j1 = jsonObject1.toString();
        System.out.println(map.getClass().isArray());
        System.out.println(s.getClass().isArray());
        System.out.println(list.getClass().isArray());
        System.out.println(map.getClass().isMemberClass());
        System.out.println("map".getClass().isPrimitive());
        System.out.println("map".getClass().isArray());

        Field[] fields = bag.getClass().getFields();
        System.out.println(fields.length);
        Object[] arrauI = new Object[1];
        System.out.println(arrauI == null);
        System.out.println(gson.toJson(arrauI));*/
        Chaild chaild = new Chaild();
        Field[] fields = chaild.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
        System.out.println(gson.toJson(chaild));



    }
}
