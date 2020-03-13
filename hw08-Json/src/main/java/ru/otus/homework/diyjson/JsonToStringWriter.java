package ru.otus.homework.diyjson;

import java.util.Collection;
import java.util.Collections;

public class JsonToStringWriter {
    private StringBuilder out;
    public void writeArray(Object[] array){

    }
    public void writeCollection(Collection coll) {
        Object[] array = coll.toArray();
        writeArray(array);
    }
}
