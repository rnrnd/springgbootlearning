package com.example.demo.loadbalance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class RobinExecute {
	/** 线程使用完不会清除该变量,会一直保留着，由于线程 池化所以不用担心内存泄漏 **/
	private final ThreadLocal<SmoothWeightRoundRobin> weightRoundRobinTl = new ThreadLocal<>();

	private final ReentrantLock lock = new ReentrantLock();

	/** 为什么添加volatile，是因为 ReentrantLock 并不保证内存可见性 **/
	private volatile SmoothWeightRoundRobin smoothWeightRoundRobin;

	public static void main(String[] args) {

		RobinExecute robinExecute = new RobinExecute();

		/** ==================    TheadLocal   ========================**/
		robinExecute.acquireWeightRoundRobinOfTheadLocal().getServerContext();

		/** ==================    ReentrantLock 可重入锁   ========================**/
		robinExecute.getLock().lock();  //notice: 注意此锁会无休止的等待资源，如果使用此锁，无比保证资源能够被拿到
		try {
			robinExecute.acquireWeightRoundRobinOfLock();
		} catch (Exception e) {
			e.printStackTrace();
		} finally { //确保一定要释放锁
			robinExecute.getLock().unlock();
		}

	}

	/**
	 * 在分布式情况，第二种使用方式  使用cas ReentrantLock 可重入锁
	 * notice:
	 * @return
	 */
	public ServerContext acquireWeightRoundRobinOfLock() {
		if (smoothWeightRoundRobin == null) {
			SmoothWeightRoundRobin weightRoundRobin = new SmoothWeightRoundRobin();
			List<SmoothWeightRoundRobinServerContext> servers = new ArrayList<>();
			servers.add(new SmoothWeightRoundRobinServerContext("191", 1, 0));
			servers.add(new SmoothWeightRoundRobinServerContext("192", 2, 0));
			servers.add(new SmoothWeightRoundRobinServerContext("194", 4, 0));
			weightRoundRobin.init(servers);
			smoothWeightRoundRobin = weightRoundRobin;
		}
		return smoothWeightRoundRobin.getServerContext();
	}

	/**
	 * 在分布式情况，第一种使用方式  ThreadLock
	 * notice: 只有在使用池化技术的情况才建议使用此方式，否则达不到效果，还会造成内存泄漏
	 * @return
	 */
	public SmoothWeightRoundRobin acquireWeightRoundRobinOfTheadLocal() {
		return Optional.ofNullable(weightRoundRobinTl.get())
				.orElseGet(() -> {
					SmoothWeightRoundRobin weightRoundRobin = new SmoothWeightRoundRobin();
					List<SmoothWeightRoundRobinServerContext> servers = new ArrayList<>();
					servers.add(new SmoothWeightRoundRobinServerContext("191", 1, 0));
					servers.add(new SmoothWeightRoundRobinServerContext("192", 2, 0));
					servers.add(new SmoothWeightRoundRobinServerContext("194", 4, 0));
					weightRoundRobin.init(servers);
					weightRoundRobinTl.set(weightRoundRobin);
					return weightRoundRobin;
				});
	}

	public ReentrantLock getLock() {
		return lock;
	}

	public ThreadLocal<SmoothWeightRoundRobin> getWeightRoundRobinTl() {
		return weightRoundRobinTl;
	}
}
