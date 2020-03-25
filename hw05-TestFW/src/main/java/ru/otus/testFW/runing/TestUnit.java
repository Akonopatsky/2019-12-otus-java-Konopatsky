package ru.otus.testFW.runing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class TestUnit {
    final private List<Method> listBefore = new ArrayList<>();
    final private List<Method> listAfter = new ArrayList<>();
    final private Method method;
    private Object testingObject;

    public TestUnit(Object testingObject, Method method) {
        this.method = method;
        this.testingObject = testingObject;
    }
    TestUnit addBefore(Method beforeMethod) {
        listBefore.add(beforeMethod);
        return this;
    }
    TestUnit addAfter(Method afterMethod) {
        listAfter.add(afterMethod);
        return this;
    }

    Exception run() throws InvocationTargetException, IllegalAccessException {
        Exception result = null;
        for (Method methodBefore : listBefore) {
            methodBefore.invoke(testingObject);
        }
        try {
            method.invoke(testingObject);
        } catch (Exception e) {
            result =  e;
        }
        for (Method methodAfter : listAfter) {
            methodAfter.invoke(testingObject);
        }
        return result;
    }
}
