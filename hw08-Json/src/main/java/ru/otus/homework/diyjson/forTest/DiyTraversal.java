package ru.otus.homework.diyjson.forTest;
import java.lang.reflect.Field;
import java.util.*;

public abstract class DiyTraversal {
    public void startTracking(Object obj) {
        if (obj== null) {
            nullMeetsAction(obj);
            return;
        }
        Class<?> clazz = obj.getClass();

        if (clazz.isArray()) {
            arrayBeforeAction(obj);
            trackArray(obj);
            endArrayAction(obj);
            return;
        }
        if (obj instanceof Collection) {
            collectionBeforeAction(obj);
            trackCollection(obj);
            collectionEndAction(obj);
            return;
        }
        if (obj instanceof Map) {
            mapBeforeAction(obj);
            trackMap(obj);
            mapEndAction(obj);
            return;
        }
        if (clazz.isPrimitive() || (clazz.getPackageName().equals("java.lang"))) {
            leafMeetAction(obj);
            return;
        }
        objectTraversal(obj);
    }

    private void objectTraversal(Object obj) {
        objectBeforeAction(obj);
        List<Field> fieldList = new ArrayList<>();
        getAllFields(obj.getClass(), fieldList);
        for (Field field : fieldList) {
            fieldBeforeAction(field, obj);
            field.setAccessible(true);
            try {
                startTracking(field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fieldEndAction(field, obj);
        }
        objectEndAction(obj);
    }

    private void getAllFields(Class<?> clazz, List<Field> fieldList) {
        Field[] fields = clazz.getDeclaredFields();
        fieldList.addAll(Arrays.asList(fields));
        Class<?> superclass = clazz.getSuperclass();
        if (superclass!=null) getAllFields(superclass,fieldList);
    }

    private void trackCollection(Object obj) {
        Collection<Object> collection = (Collection<Object>) obj;
        for (Object o : collection) {
            elementBeforeAction(o);
            startTracking(o);
            elementEndAction(o);
        }
    }

    protected abstract void elementEndAction(Object o);

    protected abstract void elementBeforeAction(Object o);

    private void trackArray(Object obj) {
        Object[] arrayOfObject = (Object[])obj;
        for (Object o : arrayOfObject) {
            elementBeforeAction(o);
            startTracking(o);
            elementEndAction(o);
        }
    }
    private void trackMap(Object obj) {
        throw new UnsupportedOperationException("map type is unsupported");
    }
    protected abstract void arrayBeforeAction(Object obj);
    protected abstract void endArrayAction(Object obj);
    protected abstract void collectionBeforeAction(Object obj);
    protected abstract void collectionEndAction(Object obj);
    protected abstract void objectBeforeAction(Object obj);
    protected abstract void objectEndAction(Object obj);
    protected abstract void fieldBeforeAction(Field field, Object obj);
    protected abstract void fieldEndAction(Field field, Object obj);
    protected abstract void mapBeforeAction(Object obj);
    protected abstract void mapEndAction(Object obj);
    protected abstract void leafMeetAction(Object obj);
    protected abstract void nullMeetsAction(Object obj);


}
