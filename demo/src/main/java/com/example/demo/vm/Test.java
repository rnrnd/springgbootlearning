package com.example.demo.vm;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

public class Test {

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();
        E e = new E();
        F f = new F();
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        System.out.println(ClassLayout.parseInstance(b).toPrintable());
        System.out.println(ClassLayout.parseInstance(c).toPrintable());
        System.out.println(ClassLayout.parseInstance(d).toPrintable());
        System.out.println(ClassLayout.parseInstance(e).toPrintable());
        System.out.println(ClassLayout.parseInstance(f).toPrintable());
        System.out.println(GraphLayout.parseInstance(c).toPrintable());
    }
}
