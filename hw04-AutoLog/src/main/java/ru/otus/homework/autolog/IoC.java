package ru.otus.homework.autolog;

import org.slf4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

class IoC<T> {
    static private Map<Method, Boolean> methodMap = new HashMap<>();
    static private Logger logger;

    public IoC(T object, Class<?> myInterface, Logger logger) {
        IoC.logger = logger;
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                methodMap.put(myInterface.getMethod(method.getName(), method.getParameterTypes()), method.isAnnotationPresent(Log.class));
            } catch (NoSuchMethodException e) {
            }
        }
    }

    private IoC() {
    }

    public T createMyClass(T object, Class<?> myInterface) {
        java.lang.reflect.InvocationHandler handler = new InvocationHandler(object);
        return (T) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{myInterface}, handler);
    }

    static class InvocationHandler<T> implements java.lang.reflect.InvocationHandler {
        private final T object;

        InvocationHandler(T object) {
            this.object = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methodMap.get(method)) {
                logIt(method, args);
            }

            return method.invoke(object, args);
        }

        private void logIt(Method method, Object[] args) {
            StringBuilder resSrting = new StringBuilder();
            resSrting.append("executed method: ")
                    .append(method.getName())
                    .append(", params:");
            if (args != null) {
                for (var arg : args) {
                    resSrting.append(" ").append(arg);
                }
            }
            logger.info(resSrting.toString());
        }

        @Override
        public String toString() {
            return "InvocationHandler{" +
                    "myClass=" + object.getClass().getName() +
                    '}';
        }
    }

}
