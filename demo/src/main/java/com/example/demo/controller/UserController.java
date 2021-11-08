package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/user")
public class UserController {

	/**
	 * 注入接口的多个实现：
	 * 1.实现类加上@Service
	 * 2.注入时声明为list
	 */
	@Autowired
	List<UserService> userServices;
	@Autowired
	ThreadPoolTaskExecutor applicationTaskExecutor;

	@GetMapping("/services")
	public Object getUserServices() {
		return userServices;
	}

	@GetMapping("/sync")
	public Object sync() throws ExecutionException, InterruptedException {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
				5,
				5,
				5,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(5),
				new ThreadPoolExecutor.CallerRunsPolicy());
		ExecutorService threadPool = Executors.newCachedThreadPool();

		CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(
				() -> userServices.get(0).getOne(),
				applicationTaskExecutor);
		CompletableFuture<String> stringCompletableFuture1 = CompletableFuture.supplyAsync(
				() -> userServices.get(1).getOne(),
				applicationTaskExecutor);
		CompletableFuture.allOf(stringCompletableFuture, stringCompletableFuture1).get();

/*		final int processors = Runtime.getRuntime().availableProcessors();
		CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.runAsync(() -> {
			userServices.get(0).getOne();
		}, threadPool);
		CompletableFuture<Void> voidCompletableFuture2 = CompletableFuture.runAsync(() -> {
			userServices.get(1).getOne();
		}, threadPool);*/




		return null;
	}
}
