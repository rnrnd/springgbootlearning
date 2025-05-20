package com.example.demo.loadbalance;

public class SmoothWeightRoundRobinServerContext implements ServerContext {

	private String server;
	private Integer originalWeight;
	private Integer currentWeight;

	public SmoothWeightRoundRobinServerContext(String server, Integer originalWeight, Integer currentWeight) {
		this.server = server;
		this.originalWeight = originalWeight;
		this.currentWeight = currentWeight;
	}
	@Override
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public Integer getOriginalWeight() {
		return originalWeight;
	}

	public void setOriginalWeight(Integer originalWeight) {
		this.originalWeight = originalWeight;
	}

	public Integer getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(Integer currentWeight) {
		this.currentWeight = currentWeight;
	}
}
