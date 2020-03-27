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

    Exception run() {
        Exception result = null;
        try {
            for (Method methodBefore : listBefore) {
                methodBefore.invoke(testingObject);
            }
            method.invoke(testingObject);
        } catch (Exception e) {
            result =  e;
        }
        finally {
            try {
                for (Method methodAfter : listAfter) {
                    methodAfter.invoke(testingObject);
                }
            } catch (Exception e) {
                if (result == null) {
                    result =  e;
                }
            }
        }
        return result;
    }
}
