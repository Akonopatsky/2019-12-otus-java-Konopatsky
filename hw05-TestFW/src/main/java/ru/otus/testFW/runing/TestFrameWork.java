package ru.otus.testFW.runing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestFrameWork {
    final private List<Method> listBefore = new ArrayList<>();
    final private List<Method> listTest = new ArrayList<>();
    final private List<Method> listAfter = new ArrayList<>();
    private Class<?> testingClass;
    private int successCount;
    private int exceptionCount;
    private int testsCount;

    public int getSuccessCount() {
        return successCount;
    }

    public int getExceptionCount() {
        return exceptionCount;
    }

    public int getTestsCount() {
        return testsCount;
    }

    public void startTesting(String className) throws ReflectiveOperationException {
        init(className);
        for (Method testMethod : listTest) {
            Object instance =testingClass.getDeclaredConstructor().newInstance();
            TestUnit testUnit = new TestUnit(instance, testMethod);
            for (Method methodBefore : listBefore) {
                testUnit.addBefore(methodBefore);
            }
            for (Method methodAfter : listAfter) {
                testUnit.addAfter(methodAfter);
            }
            runTests(testUnit);
        }
    }

    private void init(String className) throws ClassNotFoundException {
        testingClass = Class.forName(className);
        listBefore.clear();
        listAfter.clear();
        listTest.clear();
        successCount = 0;
        exceptionCount = 0;
        testsCount = 0;
        Method[] methods = testingClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Before.class)) listBefore.add(method);
            if (method.isAnnotationPresent(Test.class)) listTest.add(method);
            if (method.isAnnotationPresent(After.class)) listAfter.add(method);
        }
    }

    private void runTests(TestUnit testUnit) throws InvocationTargetException, IllegalAccessException {
        Exception exception = testUnit.run();
        testsCount++;
        if (exception == null) {
            successCount++;
        } else {
            exceptionCount++;
        }
    }
}
