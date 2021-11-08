package springboot.learning.msclass.ribbon.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.ConsulServer;
import org.springframework.cloud.consul.discovery.ConsulServerUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * 定制自己的负载均衡算法，本类的规则是根据tags某一属性相同时优先调用
 */
public class MyRibbonRule extends AbstractLoadBalancerRule {

    @Autowired
    private ConsulDiscoveryProperties consulDiscoveryProperties;
    /**
     * niws = Netflix Internal Web Service
     * @param clientConfig
     */
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        /**
         * 实现按自己的规则筛选出可用服务的逻辑步骤
         * 1.获得想要调用的微服务的实例列表
         * 2.筛选出相同机房的实例
         * 3.随机返回其中一个
         */
        ILoadBalancer loadBalancer = this.getLoadBalancer();
        List<Server> servers = loadBalancer.getReachableServers();
        List<Server> allServers = loadBalancer.getAllServers();
        List<String> tags = consulDiscoveryProperties.getTags();
        Map<String, String> metadata = ConsulServerUtils.getMetadata(tags);
        List<Server> matchServers = servers.stream().filter(server -> {
            ConsulServer consulServer = (ConsulServer) server;
            Map<String, String> targetMetadata = consulServer.getMetadata();
            return Objects.equals(metadata.get("location"), targetMetadata.get("location"));
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(matchServers)) {
            if (CollectionUtils.isEmpty(servers)) {
                return  randomChoose(allServers);
            }
            return randomChoose(servers);
        }
        return randomChoose(matchServers);
    }
    private Server randomChoose(List<Server> servers){
        int random = ThreadLocalRandom.current().nextInt(servers.size());
        return servers.get(random);
    }
}
