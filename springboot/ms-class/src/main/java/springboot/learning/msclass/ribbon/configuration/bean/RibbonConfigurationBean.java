package springboot.learning.msclass.ribbon.configuration.bean;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springboot.learning.msclass.ribbon.rule.MyRibbonRule;
import springboot.learning.msclass.ribbon.rule.MyRibbonRuleV2;

//@Configuration
public class RibbonConfigurationBean {

    /**
     * @SpringBootApplication 注解中嵌套 @ComponentScan 注解，
     * 当 @Configuration 放在可被 @ComponentScan 注解扫描到的包中时，会产生子上下文与父上下文冲突，
     * 在涉及事务的场景中可能会出现事务失效等问题,而在本类中会导致ribbon的配置被全局共享，所以放在独立的包中
     * @return
     */
    @Bean
    public IRule ribbonRule(){
        return new MyRibbonRuleV2();
    }

}
