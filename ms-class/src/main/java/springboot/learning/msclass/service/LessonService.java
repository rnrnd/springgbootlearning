package springboot.learning.msclass.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springboot.learning.msclass.domain.dto.UserDTO;
import springboot.learning.msclass.domain.dto.UserMoneyDTO;
import springboot.learning.msclass.domain.entity.Lesson;
import springboot.learning.msclass.domain.entity.LessonUser;
import springboot.learning.msclass.feign.MsUserFeignClient;
import springboot.learning.msclass.repository.LessonRepository;
import springboot.learning.msclass.repository.LessonUserRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LessonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LessonService.class);
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonUserRepository lessonUserRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private MsUserFeignClient msUserFeignClient;
    @Autowired
    private Source source;

    public Lesson buyById(Integer lessonId, Integer userId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new IllegalArgumentException("课程不存在!"));
        LessonUser lessonUser = lessonUserRepository.findByLessonIdAndUserId(lessonId, userId);
        if (lessonUser != null) {
            return lesson;
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("ms-user");
        /*List<ServiceInstance> chongqingInstance = instances.stream().filter(instance -> {
            Map<String, String> metadata = instance.getMetadata();
            String local = metadata.get("local");
            if ("chongqing".equals(local)) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        URI uri = chongqingInstance.get(0).getUri();*/
        /*int i = ThreadLocalRandom.current().nextInt(instances.size());
        URI uri = instances.get(i).getUri();
        LOGGER.info("micro service url is {} " ,uri);*/
        /*UserDTO userDTO = restTemplate.getForObject(
                 "http://ms-user/users/{userId}",
                UserDTO.class,
                userId
        );*/
        UserDTO userDTO = msUserFeignClient.findById(userId);
        BigDecimal money = userDTO.getMoney().subtract(lesson.getPrice());
        if (money.doubleValue() < 0) {
            throw new IllegalArgumentException("余额不足！");
        }
        //发送消息给微服务让它扣减金额
        source.output().send(
                MessageBuilder.withPayload(
                        new UserMoneyDTO(
                                userId,
                                lesson.getPrice(),
                                "购买课程",
                                String.format("%s购买了id为%S的课程", userId, lessonId)
                        )
                ).build());

        LessonUser lu = new LessonUser();
        lu.setLeesonId(lessonId);
        lu.setUserId(userId);
        lessonUserRepository.save(lu);
        return lesson;
    }

    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }
}
