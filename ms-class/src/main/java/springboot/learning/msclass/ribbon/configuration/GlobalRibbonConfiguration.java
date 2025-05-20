package springboot.learning.msclass.ribbon.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import springboot.learning.msclass.ribbon.configuration.bean.RibbonConfigurationBean;

/**
 * 指定ribbon的全局配置
 */

@Configuration
@RibbonClients(defaultConfiguration = RibbonConfigurationBean.class)
public class GlobalRibbonConfiguration {
}
