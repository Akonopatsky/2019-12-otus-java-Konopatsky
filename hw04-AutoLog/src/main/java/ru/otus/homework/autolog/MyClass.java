package ru.otus.homework.autolog;

public class MyClass implements MyClassInterface {
    @Override
    @Log
    public String method2(int a, double b) {
        System.out.println("method2");
        return "method2";
    }

    @Override
    public long method3(String d) {
        System.out.println("method3");
        return 42;
    }
    @Log
    public void method1() {
        System.out.println("method1");
    }

    public static void main(String[] args) {
        MyClass myClass = new MyClass();
        MyClassInterface proxytestClass = IoC.createMyClass(myClass);
        proxytestClass.method1();
    }
}
