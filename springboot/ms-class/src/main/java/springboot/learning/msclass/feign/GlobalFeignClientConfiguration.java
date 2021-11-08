package springboot.learning.msclass.feign;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * 最好不要加 @Configuration 注解
 * 否则必须放到启动类所在包的外面，即 @ComponentScan 注解扫描不到的地方
 * 否则会被所有的FeignClient共享，与ribbon同理
 */
public class GlobalFeignClientConfiguration {

    @Bean
    public Logger.Level loggerLevel(){
        return Logger.Level.FULL;
    }

}
