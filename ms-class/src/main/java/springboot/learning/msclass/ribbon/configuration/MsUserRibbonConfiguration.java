package springboot.learning.msclass.ribbon.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;
import springboot.learning.msclass.ribbon.configuration.bean.RibbonConfigurationBean;

/**
 * 为某个微服务指定配置，可以有两只方式，代码和配置文件，本类为代码实现
 * 还可以在配置文件中指定，如：
 * ms-user: #微服务名
 * ribbon:
 * NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule #具体的配置
 */
@Configuration
@RibbonClient(name = "ms-user", configuration = RibbonConfigurationBean.class)
public class MsUserRibbonConfiguration {
}
