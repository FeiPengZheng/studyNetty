package com.example.springbootdemo;

import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

public class TestJDKDynamicProxy {
    interface IHello{
        void sayHello();
    }


    static class Hello implements IHello{
        @Override
        public void sayHello() {
            System.out.println("hello world");
        }
    }

     static class DynamicProxy implements InvocationHandler{

        private Object original;

        Object bind(Object original){
            this.original = original;
            return Proxy.newProxyInstance(original.getClass().getClassLoader(),original.getClass().getInterfaces(),this);
        }

         @Override
         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
             System.out.println("welcome");
            return method.invoke(original,args);
         }
     }


    public static void main(String[] args) throws Exception {
        byte[] hellos = ProxyGenerator.generateProxyClass(
                "hello", new Class[]{IHello.class}, Modifier.PUBLIC | Modifier.FINAL);
        FileOutputStream fileOutputStream = new FileOutputStream("D://hello.class");
        fileOutputStream.write(hellos);

        /*IHello hello = (IHello) new DynamicProxy().bind(new Hello());
        hello.sayHello();
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");*/

    }










}
