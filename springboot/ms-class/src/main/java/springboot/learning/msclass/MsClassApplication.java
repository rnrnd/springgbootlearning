package springboot.learning.msclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springboot.learning.msclass.feign.GlobalFeignClientConfiguration;
import springboot.learning.msclass.rabbit.MySource;

@SpringBootApplication
@EnableFeignClients(defaultConfiguration = GlobalFeignClientConfiguration.class /*全局配置*/)
@EnableBinding({Source.class, MySource.class})
public class MsClassApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsClassApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
