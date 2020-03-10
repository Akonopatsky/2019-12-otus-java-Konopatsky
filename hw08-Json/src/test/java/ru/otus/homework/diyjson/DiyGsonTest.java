package ru.otus.homework.diyjson;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DiyGsonTest {
private BagOfPrimitives[] array1;
private List<BagOfPrimitives> list1 = new ArrayList<>();
private Map<Integer, BagOfPrimitives> map1 = new HashMap<>();
DiyGson diyGson = new DiyGson();
private Gson gson = new Gson();

    @BeforeEach
    void setUp() {

    }
    @Test
    @DisplayName("array of objects")
    void arrayOfObjacts() {
        String json = diyGson.toJson(array1);
        BagOfPrimitives[] array2 = gson.fromJson(json, BagOfPrimitives[].class);
        assertEquals(array1, array2);
    }
    @Test
    @DisplayName("double")
    void testDouble() {
        double d1 = 12354.234d;
        String json = diyGson.toJson(d1);
        double d2 = gson.fromJson(json, double.class);
        assertEquals(d1,d2);
    }
    @Test
    @DisplayName("hashmap")
    void testhashMap() {
        String json = diyGson.toJson(map1);
        Map<Integer, BagOfPrimitives> map2 = gson.fromJson(json, HashMap.class);
        assertEquals(map1,map2);
    }
}