package ru.otus.homework.diyjson;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.diyjson.forTest.BagOfPrimitives;
import ru.otus.homework.diyjson.forTest.Child;

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
        array1 = new BagOfPrimitives[4];
        array1[0] = new BagOfPrimitives(1,"1val2", 2);
        array1[1] = new BagOfPrimitives(3,"2val2", 4);
        array1[2] = new BagOfPrimitives(5,"3val2", 6);

    }
    @Test
    @DisplayName("array of objects")
    void arrayOfObjacts() {
        String json = diyGson.toJson(array1);
        BagOfPrimitives[] array2 = gson.fromJson(json, BagOfPrimitives[].class);
        assertArrayEquals(array1, array2);
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
    @DisplayName("List")
    void testList() {
        Child child1 = new Child();
        BagOfPrimitives[] array2;
        array2 = new BagOfPrimitives[3];
        array2[0] = new BagOfPrimitives(1,"1val2", 2);
        array2[1] = new BagOfPrimitives(3,"2val2", 4);
        array2[2] = new BagOfPrimitives(5,"3val2", 6);
        child1.bagOfPrimitivesList.add(array1);
        child1.bagOfPrimitivesList.add(array2);
        String json = diyGson.toJson(child1);
        Child child2 = gson.fromJson(json, Child.class);
        assertEquals(child1,child2);
    }
}