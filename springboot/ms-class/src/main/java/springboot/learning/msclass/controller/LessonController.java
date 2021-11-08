package springboot.learning.msclass.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.learning.msclass.domain.entity.Lesson;
import springboot.learning.msclass.service.LessonService;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("/lessons")
public class LessonController {
    public static final Logger LOGGER = LoggerFactory.getLogger(LessonController.class);
    @Autowired
    private LessonService lessonService;

    /**
     * 服务容错的常见手段：
     * 1.超时
     * 2.限流
     * 3.仓壁模式
     * 4.断路器模式
     * 5.重试
     */
    /**
     * 注解的执行顺序，越小越先执行，LOWEST_PRECEDENCE = Integer.MAX_VALUE
     * Bulkhead : LOWEST_PRECEDENCE
     * TimeLimiter : LOWEST_PRECEDENCE - 1
     * RateLimiter : LOWEST_PRECEDENCE - 2
     * CircuitBreaker : LOWEST_PRECEDENCE - 3
     * Retry : LOWEST_PRECEDENCE - 4
     */
    @GetMapping("/buy/{lessonId}")
    @Bulkhead(name = "buyById", fallbackMethod = "buyByIdFallback")
    @RateLimiter(name = "buyById", fallbackMethod = "buyByIdFallback")//也可以加在方法上
    @Retry(name = "buyById", fallbackMethod = "buyByIdFallback")
    @CircuitBreaker(name = "buyById",fallbackMethod = "buyByIdFallback")
    //@TimeLimiter(name = "buyById", fallbackMethod = "buyByIdFallback")
    public Lesson buyById(@PathVariable Integer lessonId) throws InterruptedException {
        /*Lesson lesson = new Lesson();
        lesson.setId(1);
        lesson.setTitle("微服务和springcloud实战");
        lesson.setDescription("微服务和springcloud实战");
        lesson.setPrice(BigDecimal.valueOf(200.0));
        lesson.setCreateTime(new Date());
        lesson.setCover("");
        lesson.setVideoUrl("");
        lessonService.save(lesson);*/
        Thread.sleep(1000);
        return lessonService.buyById( lessonId, 1);
    }
    public  Lesson buyByIdFallback(Integer lessonId, Throwable throwable){
        LOGGER.info("发生fallback", throwable);
        return new Lesson();
    }
}
