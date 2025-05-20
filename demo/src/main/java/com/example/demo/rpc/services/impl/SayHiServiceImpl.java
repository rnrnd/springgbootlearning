package com.example.demo.rpc.services.impl;

import com.example.demo.rpc.services.SayHiService;

public class SayHiServiceImpl implements SayHiService {

    @Override
    public String sayHi(String name) {
        return "Hi, " + name;
    }
}
