package springboot.learning.gateway;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自己实现的谓词规则必须以RoutePredicateFactory结尾，RoutePredicateFactory之前的作为配置的key
 */
@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeConfig config) {
        LocalTime startTime = config.getStartTime();
        LocalTime endTime = config.getEndTime();
        LocalTime now = LocalTime.now();
        return serverWebExchange -> now.isAfter(startTime) && now.isBefore(endTime);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("startTime", "endTime");
    }

    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        String format = dateTimeFormatter.format(LocalTime.now());
        System.out.println(format);
    }
}
