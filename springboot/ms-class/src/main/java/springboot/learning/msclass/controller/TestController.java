package springboot.learning.msclass.controller;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.collection.Seq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.learning.msclass.rabbit.MySource;

import java.util.List;

@RefreshScope
@RestController
public class TestController {

    @Value("${first.config}")
    private String config;
    @GetMapping("/test-config")
    public String testConfig(){
        return this.config;
    }

    @Autowired
    private DiscoveryClient discoveryClient;
    @GetMapping("/test-discovery")
    public List<ServiceInstance> testDiscovery(){
        return discoveryClient.getInstances("ms-user");
    }

    @Autowired
    private BulkheadRegistry bulkheadRegistry;
    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;
    @Autowired
    private RateLimiterRegistry rateLimiterRegistry;
    @GetMapping("/rate-limiter-configs")
    public Seq<RateLimiter> getRateLimiterConfigs(){
        //Seq<Bulkhead> allBulkheads = bulkheadRegistry.getAllBulkheads();
        //Seq<CircuitBreaker> allCircuitBreakers = circuitBreakerRegistry.getAllCircuitBreakers();
        Seq<RateLimiter> allRateLimiters = rateLimiterRegistry.getAllRateLimiters();
        return allRateLimiters;
    }

    @Autowired
    private Source source;
    @GetMapping("/test-stream-v1")
    public boolean testStreamV1(){
        return source.output().send((Message<?>) MessageBuilder
                        .withPayload("<v1消息内容>")
                        .setHeader("version", "v1")
                        .build()
                );
    }
    @GetMapping("/test-stream-v2")
    public boolean testStreamV2(){
        return source.output().send((Message<?>) MessageBuilder
                        .withPayload("<v2消息内容>")
                        .setHeader("version", "v2")
                        .build()
                );
    }

    @Autowired
    private MySource mySource;
    @GetMapping("/test-stream-2")
    public boolean testStream2(){
        return mySource.output().send((Message<?>) MessageBuilder.withPayload("<消息内容>").build());
    }

}
