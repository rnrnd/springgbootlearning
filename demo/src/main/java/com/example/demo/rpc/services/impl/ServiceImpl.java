package com.example.demo.rpc.services.impl;

import com.example.demo.rpc.services.Service;

public class ServiceImpl implements Service {

    @Override
    public String sayHi(String name) {
        return "Hi, " + name;
    }
}
