package springboot.learning.msuser.rabbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import springboot.learning.msuser.domain.dto.UserMoneyDTO;
import springboot.learning.msuser.service.UserService;

@Component
public class LessonBuyStreamListener {
    @Autowired
    private UserService userService;

    @StreamListener(Sink.INPUT)
    public void lessobBuy(UserMoneyDTO moneyDTO) {
        userService.lessonBuy(moneyDTO);
    }
}
