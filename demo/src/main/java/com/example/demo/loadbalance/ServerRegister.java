package com.example.demo.loadbalance;

import java.util.HashMap;
import java.util.Map;

public class ServerRegister {
	public static final Map<String, SmoothWeightRoundRobinServer> serverMap = new HashMap<>();
	static {
		serverMap.put("A", new SmoothWeightRoundRobinServer("A",2,2));
		serverMap.put("B", new SmoothWeightRoundRobinServer("B",3,3));
		serverMap.put("C", new SmoothWeightRoundRobinServer("C",5,5));
	}
}
