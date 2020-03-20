package ru.otus.homework.diyjson.OldAlternativeVersion;

import ru.otus.homework.diyjson.DiyJsonWriter;

import java.lang.reflect.Field;

// старая версия ДЗ
// формирование json на основе абстрактого обходчика обьекта

public class DiyGsonWithoutJavax extends DiyTraversal implements DiyJsonWriter {
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

}
