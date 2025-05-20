package springboot.learning.msclass.rabbit;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

public interface MySource {

    String MY_OUTPUT = "myOutput";

    /**
     * @return output channel
     */
    @Output(MY_OUTPUT)
    MessageChannel output();
}
