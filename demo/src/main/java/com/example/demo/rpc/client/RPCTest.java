package com.example.demo.rpc.client;

import com.example.demo.rpc.services.SayHiService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class RPCTest {

    public static void main(String[] args) throws IOException {

        SayHiService service = RPCClient.getRemoteProxyObj(SayHiService.class, new InetSocketAddress("localhost", 8088));
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.next();
            System.out.println(service.sayHi(input));
        }

    }

}
