package springboot.learning.msclass.ribbon.rule;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.QueryParams;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.ConsulServer;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MyRibbonRuleV2 extends AbstractLoadBalancerRule {

    @Autowired
    private ConsulDiscoveryProperties consulDiscoveryProperties;
    @Autowired
    private ConsulClient consulClient;

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        ILoadBalancer loadBalancer = this.getLoadBalancer();
        ZoneAwareLoadBalancer zoneAwareLoadBalancer = (ZoneAwareLoadBalancer) loadBalancer;
        //想要调用的微服务名称，即 ms-user
        String name = zoneAwareLoadBalancer.getName();
        List<String> tags = consulDiscoveryProperties.getTags();
        String location = tags.stream()
                .filter(tag -> tag.startsWith("location"))
                .findFirst()
                .orElse(null);
        HealthServicesRequest healthServicesRequest = HealthServicesRequest.newBuilder()
                .setTag(location)
                .setPassing(true)
                .setQueryParams(QueryParams.DEFAULT)
                .build();
        Response<List<HealthService>> response = consulClient.getHealthServices(name, healthServicesRequest);
        //Response<List<HealthService>> response = this.consulClient.getHealthServices(name, location, true, QueryParams.DEFAULT);
        List<HealthService> healthServices = response.getValue();
        if (CollectionUtils.isEmpty(healthServices)) {
            Response<List<HealthService>> allResponse = this.consulClient.getHealthServices(name, null, true, QueryParams.DEFAULT);
            healthServices = allResponse.getValue();
        }
        List<ConsulServer> consulServers = healthServices.stream().map(ConsulServer::new).collect(Collectors.toList());
        return randomChoose(consulServers);
    }
    private Server randomChoose(List<ConsulServer> servers){
        int random = ThreadLocalRandom.current().nextInt(servers.size());
        return servers.get(random);
    }
}
