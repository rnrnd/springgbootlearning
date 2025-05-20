package com.example.demo.loadbalance;

import java.util.HashMap;
import java.util.Map;

public class ServerRegister {
	public static final Map<String, SmoothWeightRoundRobinServerContext> serverMap = new HashMap<>();
	static {
		serverMap.put("A", new SmoothWeightRoundRobinServerContext("A",2,2));
		serverMap.put("B", new SmoothWeightRoundRobinServerContext("B",2,2));
		serverMap.put("C", new SmoothWeightRoundRobinServerContext("C",2,2));
	}
}
