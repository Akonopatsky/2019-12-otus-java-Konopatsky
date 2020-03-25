package ru.otus.homework.autolog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyClassDemo implements MyClassInterface {
    static private Logger logger = LoggerFactory.getLogger(MyClassDemo.class);
    private String demoStrinng = "_";

    public void setDemoStrinng(String demoStrinng) {
        this.demoStrinng = demoStrinng;
    }

    @Override
    @Log
    public void method1() {
        System.out.println("method1" + " demoStrinng = " + demoStrinng);
    }

    @Override
    public String method2(int a) {
        System.out.println("method2" + " demoStrinng = " + demoStrinng);
        return String.valueOf(a);
    }

    @Log
    @Override
    public long method3(String s) {
        System.out.println("method3 " + " demoStrinng = " + demoStrinng);
        return s.length();
    }

    public static void main(String[] args) {
        MyClassDemo myClassDemo = new MyClassDemo();
        myClassDemo.setDemoStrinng("demo1");
        IoC<MyClassInterface> ioc = new IoC<>(new MyClassDemo(), MyClassInterface.class, logger);
        MyClassInterface proxytestClass = ioc.createMyClass(myClassDemo, MyClassInterface.class);
        proxytestClass.method1();
        proxytestClass.method2(42);
        proxytestClass.method3("third method");
    }
}
