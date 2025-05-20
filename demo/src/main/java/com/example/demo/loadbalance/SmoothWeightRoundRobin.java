package com.example.demo.loadbalance;

import java.util.ArrayList;
import java.util.List;

public class SmoothWeightRoundRobin implements Robin{
	/**初始化所有的服务器**/
	List<SmoothWeightRoundRobinServerContext> servers = new ArrayList<>();

	/**服务器权重总和*/
	private int weightCount;

	public void init(List<SmoothWeightRoundRobinServerContext> servers) {
		this.servers = servers;
		this.weightCount = this.servers.stream().map(SmoothWeightRoundRobinServerContext::getOriginalWeight).reduce(0, (l, r) -> l + r);

	}
	/**
	 * 平滑加权轮询算法
	 * @return
	 */
	public SmoothWeightRoundRobinServerContext getServerContext(){
		int totalWeights = ServerRegister.serverMap.values().stream().mapToInt(SmoothWeightRoundRobinServerContext::getOriginalWeight).sum();

		SmoothWeightRoundRobinServerContext maxCurrentWeightServer = null;
		for (SmoothWeightRoundRobinServerContext server : ServerRegister.serverMap.values()) {
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
			new Thread(() -> System.out.println(robin.getServerContext().getServer())).start();
		}
	}

}
