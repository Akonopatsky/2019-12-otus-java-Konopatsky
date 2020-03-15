package ru.otus.homework.diyjson;

import com.google.gson.Gson;
import ru.otus.homework.diyjson.forTest.BagOfPrimitives;
import ru.otus.homework.diyjson.forTest.Child;
import ru.otus.homework.diyjson.forTest.EmptyClass;

import java.lang.reflect.Field;
import java.util.*;

public class DiyGson extends DiyTraversal {
private StringBuilder jsonStrig = new StringBuilder();

    public String toJson(Object obj) {
        jsonStrig = new StringBuilder();
        startTracking(obj);
        return jsonStrig.toString();
    }

    @Override
    protected void fieldBeforeAction(Field field, Object obj) {
        jsonStrig.append("\"").append(field.getName()).append("\"");
        jsonStrig.append(":");
    }

    @Override
    protected void fieldEndAction(Field field, Object obj) {
        jsonStrig.append(",");
    }

    @Override
    protected void elementEndAction(Object o) {
        jsonStrig.append(",");
    }

    @Override
    protected void elementBeforeAction(Object o) {

    }

    @Override
    protected void leafMeetAction(Object obj) {
        if (obj instanceof String) jsonStrig.append("\"");
        jsonStrig.append(obj.toString());
        if (obj instanceof String) jsonStrig.append("\"");
    }

    @Override
    protected void nullMeetsAction(Object obj) {
        jsonStrig.append("null");
    }

    @Override
    protected void endArrayAction(Object obj) {
        deleteLastComma();
        jsonStrig.append("]");
    }

    @Override
    protected void arrayBeforeAction(Object obj) {
        jsonStrig.append("[");
    }
    @Override
    protected void collectionBeforeAction(Object obj) {
        jsonStrig.append("[");
    }

    @Override
    protected void collectionEndAction(Object obj) {
        deleteLastComma();
        jsonStrig.append("]");
    }

    @Override
    protected void objectBeforeAction(Object obj) {
        jsonStrig.append("{");
    }
    @Override
    protected void objectEndAction(Object obj) {
        deleteLastComma();
        jsonStrig.append("}");
    }

    private void deleteLastComma() {
        if (jsonStrig.charAt(jsonStrig.length()-1)==',') jsonStrig.deleteCharAt(jsonStrig.length()-1);
    }

    @Override
    protected void mapBeforeAction(Object obj) {    }

    @Override
    protected void mapEndAction(Object obj) {    }


    public static void main(String[] args) throws IllegalAccessException {
        String[] array = {"aa","bb"};
        List<String> list = Arrays.asList("dd",null,"dhhh");
        BagOfPrimitives bag = new BagOfPrimitives(22, "test", 10);
        DiyGson diyGson = new DiyGson();
        Gson gson = new Gson();
        Map<String,String[]> map = new HashMap<>();
        map.put("mapkey",array);
Integer a = 34;

        Child child = new Child();
        EmptyClass empty = new EmptyClass();
        boolean f = false;

       System.out.println(gson.toJson(child));
       System.out.println(diyGson.toJson(child));
       System.out.println(gson.toJson(f));
       System.out.println(diyGson.toJson(f));
       System.out.println(gson.toJson(34));
       System.out.println(diyGson.toJson(34));

/*        diyGson.toJson(array);
        diyGson.toJson(list);
        Object[] arrayOfObj = new Object[5];
        arrayOfObj[0] = list;
        arrayOfObj[1] = array;
        arrayOfObj[2] = bag;
        arrayOfObj[3] = map;
        Object[] arr2 = new Object[2];
        arr2[0] = arrayOfObj;

        Map<BagOfPrimitives, BagOfPrimitives> mapObj = new HashMap<>();
        BagOfPrimitives b1 = new BagOfPrimitives(11, "key", 22);
        BagOfPrimitives b2 = new BagOfPrimitives(31, "value", 33);
        mapObj.put( b1, b2);
        System.out.println(gson.toJson(mapObj));*/
/*        System.out.println(gson.toJson(bag));
        System.out.println(gson.toJson(map));
        System.out.println(gson.toJson(list));*/


/*        var jsonObject = Json.createObjectBuilder()
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
        System.out.println(j1);*/






    }
}
