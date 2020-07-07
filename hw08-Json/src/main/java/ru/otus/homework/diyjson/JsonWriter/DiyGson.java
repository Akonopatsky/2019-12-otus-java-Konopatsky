package ru.otus.homework.diyjson.JsonWriter;

import ru.otus.homework.diyjson.DiyJsonWriter;
import ru.otus.homework.diyjson.UnsupportedTypeException;

import javax.json.*;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.*;

public class DiyGson implements DiyJsonWriter {

    @Override
    public String toJson(Object obj) throws UnsupportedTypeException {
        JsonValue json = startTracking(obj);
        Writer writer = new StringWriter();
        Json.createWriter(writer).write(json);
        return json.toString();
    }

    private JsonValue startTracking(Object obj) throws UnsupportedTypeException {
        Class<?> clazz = obj.getClass();
        if (clazz.isArray()) {
            return trackArray(obj);
        }
        if (obj instanceof Collection) {
            return trackCollection(obj);
        }
        if (obj instanceof Map) {
            return trackMap(obj);
        }
        if (isValueType(clazz)) {
            return getJsonValue(obj);
        }
        return objectTraversal(obj);
    }

    private JsonValue objectTraversal(Object obj) throws UnsupportedTypeException {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        List<Field> fieldList = new ArrayList<>();
        getAllFields(obj.getClass(), fieldList);
        for (Field field : fieldList) {
            boolean accessible = field.canAccess(obj);
            field.setAccessible(true);
            try {
                if (field.get(obj) == null) jsonObjectBuilder.addNull(field.getName());
                else jsonObjectBuilder.add(field.getName(), startTracking(field.get(obj)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(accessible);
        }
        return jsonObjectBuilder.build();
    }

    private JsonValue trackArray(Object obj) throws UnsupportedTypeException {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        Object[] arrayOfObject = (Object[]) obj;
        for (Object o : arrayOfObject) {
            if (o == null) jsonArrayBuilder.addNull();
            else jsonArrayBuilder.add(startTracking(o));
        }
        return jsonArrayBuilder.build();
    }

    private JsonValue trackCollection(Object obj) throws UnsupportedTypeException {
        Collection<Object> collection = (Collection<Object>) obj;
        return trackArray(collection.toArray());
    }

    private JsonValue getJsonValue(Object obj) throws UnsupportedTypeException {
        if (obj instanceof Boolean) return (Boolean) obj ? JsonValue.TRUE : JsonValue.FALSE;
        if (obj instanceof Long) return Json.createValue((Long) obj);
        if (obj instanceof Integer) return Json.createValue((Integer) obj);
        if (obj instanceof Byte) return Json.createValue((Byte) obj);
        if (obj instanceof Short) return Json.createValue((Short) obj);
        if (obj instanceof Double) return Json.createValue((Double) obj);
        if (obj instanceof Float) return Json.createValue((Float) obj);
        if (obj instanceof String) return Json.createValue((String) obj);
        if (obj instanceof StringBuilder) return Json.createValue(obj.toString());
        if (obj instanceof StringBuffer) return Json.createValue(obj.toString());
        throw new UnsupportedTypeException("unsupported type " + obj.getClass().toString());
    }

    private boolean isValueType(Class<?> clazz) {
        return (clazz.getPackageName().equals("java.lang"));
    }

    private void getAllFields(Class<?> clazz, List<Field> fieldList) {
        Field[] fields = clazz.getDeclaredFields();
        fieldList.addAll(Arrays.asList(fields));
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null) getAllFields(superclass, fieldList);
    }

    private JsonValue trackMap(Object obj) throws UnsupportedTypeException {
        throw new UnsupportedTypeException("map is unsupported in this version ");
    }
}

