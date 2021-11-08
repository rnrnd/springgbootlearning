package com.example.demo.collection;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class CollectionsFrameworkTest {

    public static void main(String[] args) {
        List<Object> list = Collections.emptyList();
        Class clazz = list.getClass();
        EnumSet enumSet0 = EnumSet.noneOf(clazz);
        EnumSet enumSet1 = EnumSet.allOf(clazz);
    }
}
