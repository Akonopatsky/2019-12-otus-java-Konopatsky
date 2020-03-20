package ru.otus.homework.autolog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class IoC {

  static MyClassInterface createMyClass(MyClassInterface myclass) {
    InvocationHandler handler = new DemoInvocationHandler(myclass);
    return (MyClassInterface) Proxy.newProxyInstance(IoC.class.getClassLoader(),
        new Class<?>[]{MyClassInterface.class}, handler);
  }

  static class DemoInvocationHandler implements InvocationHandler {
    private final MyClassInterface myClass;

    DemoInvocationHandler(MyClassInterface myClass) {
      this.myClass = myClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      System.out.println("invoking method:" + method);
      return method.invoke(myClass, args);
    }

    @Override
    public String toString() {
      return "DemoInvocationHandler{" +
                 "myClass=" + myClass +
                 '}';
    }
  }

}
