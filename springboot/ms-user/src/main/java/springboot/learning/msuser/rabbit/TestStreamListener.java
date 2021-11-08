package springboot.learning.msuser.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Component;

@Component
public class TestStreamListener {

    public static final Logger LOGGER = LoggerFactory.getLogger(TestStreamListener.class);

    @StreamListener(value = Sink.INPUT, condition = "headers['version'] == 'v1'")
    public void testV1(String message){
        LOGGER.info("消费V1消息， message = {}", message);
    }
    @StreamListener(value = Sink.INPUT, condition = "headers['version'] == 'v2'")
    public void testV2(String message){
        LOGGER.info("消费V2消息， message = {}", message);
    }

    @StreamListener(MySink.MY_INPUT)
    public void test2(String message) throws Exception {
        LOGGER.info("自定义消费消息， message = {}", message);
        //throw new Exception("出现异常.....");
    }

    /**
     * 全局异常处理
     * @param message 异常信息
     */
    @StreamListener("errorChannel")
    public void error(Message<?> message){
        ErrorMessage errorMessage = (ErrorMessage) message;
        LOGGER.error("Handling Error: {}", errorMessage);
    }
}
