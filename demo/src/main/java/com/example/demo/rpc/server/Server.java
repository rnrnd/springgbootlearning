package com.example.demo.rpc.server;

import java.io.IOException;

public interface Server {

    void stop() throws IOException;


    void start() throws IOException;


    void register(Class<?> serviceInterface, Class<?> impl);


    boolean isRunning();


    int getPort();
}
