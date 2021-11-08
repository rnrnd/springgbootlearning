package com.example.demo.loadbalance;

import java.util.ArrayList;
import java.util.List;

public class SmoothWeightRoundRobin implements Robin{
	/**初始化所有的服务器**/
	List<SmoothWeightRoundRobinServer> servers = new ArrayList<>();

	/**服务器权重总和*/
	private int weightCount;

	public void init(List<SmoothWeightRoundRobinServer> servers) {
		this.servers = servers;
		this.weightCount = this.servers.stream().map(SmoothWeightRoundRobinServer::getOriginalWeight).reduce(0, (l, r) -> l + r);

	}
	/**
	 * 平滑加权轮询算法
	 * @return
	 */
	public SmoothWeightRoundRobinServer getServer(){
		int totalWeights = ServerRegister.serverMap.values().stream().mapToInt(SmoothWeightRoundRobinServer::getOriginalWeight).sum();

		SmoothWeightRoundRobinServer maxCurrentWeightServer = null;
		for (SmoothWeightRoundRobinServer server : ServerRegister.serverMap.values()) {
			if ( maxCurrentWeightServer == null || server.getCurrentWeight() > maxCurrentWeightServer.getCurrentWeight()) {
				maxCurrentWeightServer = server;
			}
		}
		assert maxCurrentWeightServer != null;
		maxCurrentWeightServer.setCurrentWeight(maxCurrentWeightServer.getCurrentWeight() - totalWeights);
		return maxCurrentWeightServer;
	}

	public static void main(String[] args) {
		Robin robin = new SmoothWeightRoundRobin();
		for (int i = 0; i < 10; i++) {
			new Thread(() -> System.out.println(robin.getServer().getServer())).start();
		}
	}
}
