package ru.otus.homework.diyjson;


import java.util.Collection;
import java.util.Map;

public abstract class ObjectParser {
    public void StartTracking(Object obj) {
        if (obj== null) {
            nullMeetsAction(obj);
            return;
        }
        Class<?> clazz = obj.getClass();
        if (clazz.isPrimitive()) {
            primitiveMeetAction(obj);
            return;
        }
        if (clazz.isArray()) {
            arrayStartAction(obj);
            trackArray(obj);
            endArrayAction(obj);
            return;
        }
        if (obj instanceof Collection) {
            collectionStartAction(obj);
            trackCollection(obj);
            collectionEndAction(obj);
            return;
        }
        if (obj instanceof Map) {
            mapStartAction(obj);
            trackMap(obj);
            mapEndAction(obj);
            return;
        }


    }

    protected abstract void mapEndAction(Object obj);
    protected abstract void mapStartAction(Object obj);
    protected abstract void primitiveMeetAction(Object obj);
    protected abstract void nullMeetsAction(Object obj);
    protected abstract void endArrayAction(Object obj);
    protected abstract void arrayStartAction(Object obj);
    protected abstract void collectionEndAction(Object obj);
    protected abstract void collectionStartAction(Object obj);

    private void trackCollection(Object obj) {
        Collection<Object> collection = (Collection<Object>) obj;
        for (Object o : collection) {

        }

    }
    private void trackArray(Object obj) {
        throw new UnsupportedOperationException();
    }
    private void trackMap(Object obj) {
        throw new UnsupportedOperationException();
    }







}
