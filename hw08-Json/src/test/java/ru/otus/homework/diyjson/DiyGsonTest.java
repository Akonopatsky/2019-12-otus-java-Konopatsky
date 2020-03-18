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
    private DiyGson diyGson = new DiyGson();
    private Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        array1 = new BagOfPrimitives[5];
        array1[0] = new BagOfPrimitives(1,"1val2", 2);
        array1[1] = new BagOfPrimitives(3,"2val2", 4);
        array1[2] = new BagOfPrimitives(5,"3val2", 6);
        array1[3] = new BagOfPrimitives(0,"",0);

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
        Child crazyInstance1 = new Child();
        BagOfPrimitives[] array2;
        array2 = new BagOfPrimitives[3];
        array2[0] = new BagOfPrimitives(1,"1val2", 2);
        array2[1] = new BagOfPrimitives(3,"2val2", 4);
        array2[2] = new BagOfPrimitives(5,"3val2", 6);
        crazyInstance1.bagOfPrimitivesList.add(array1);
        crazyInstance1.bagOfPrimitivesList.add(array2);
        String json = diyGson.toJson(crazyInstance1);
        Child crazyInstance2 = gson.fromJson(json, Child.class);
        assertTrue(crazyInstance1.equals(crazyInstance2));
    }

}