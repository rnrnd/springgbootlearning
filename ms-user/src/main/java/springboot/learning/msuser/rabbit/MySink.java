package springboot.learning.msuser.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {

    String MY_INPUT = "myInput";

    /**
     * @return input channel.
     */
    @Input(MY_INPUT)
    SubscribableChannel input();
}
