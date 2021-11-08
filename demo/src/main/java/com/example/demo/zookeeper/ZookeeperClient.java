package com.example.demo.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

public class ZookeeperClient {
	//private static final String HOST = "i-2ze8um4r4f2g05py6lc2";
	private static final String HOST = "182.92.234.64";
	//连接的zk服务
	private static final String CONNECTION_HOSTS =
			HOST + ":2181," +
			HOST + ":2182," +
			HOST + ":2183";
	//超时连接的时间
	private static final int SESSION_TIMEOUT = 2000;

	private static ZooKeeper zooKeeper = null;
	//servers根目录
	private volatile static  String serviceRootNode = "/services";
	//服务器列表
	/**使用volatile的意义
	 * 1、通过在总线加LOCK#锁的方式
	 * 2、通过缓存一致性协议
	 * 1.volatile关键字的两层语义
	 一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：
	 1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
	 2）禁止进行指令重排序。
	 * */
	private static final List<String> serviceList = new ArrayList<String>();
	static {
		try {
			zooKeeper = new ZooKeeper(CONNECTION_HOSTS, SESSION_TIMEOUT, new Watcher() {

				/**
				 * 监听服务器列表的变化
				 * */
				public void process(WatchedEvent watchedEvent) {
					try {
						getServerList();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取服务器列表
	 * */
	public static void getServerList() throws Exception {
		List<String> nodes = zooKeeper.getChildren(serviceRootNode,true);
		//清空服务器列表信息list集合
		serviceList.clear();
		for(String node : nodes){
			/**
			 * 这里的第二个参数：false，意思是已经获到了连接，不需要重新获取连接
			 * */
			byte[] data = zooKeeper.getData(serviceRootNode + "/"+ node,false,null);
			serviceList.add(new String(data));
		}
		System.out.println(serviceList);
	}
	public static void main(String[] args) throws Exception {
		ZookeeperClient zookeeperClient = new ZookeeperClient();
		zookeeperClient.getServerList();
		Thread.sleep(Long.MAX_VALUE);
	}
}
