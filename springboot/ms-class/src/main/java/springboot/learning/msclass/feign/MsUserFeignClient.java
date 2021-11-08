package springboot.learning.msclass.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springboot.learning.msclass.domain.dto.UserDTO;

@FeignClient(name = "ms-user"/*, configuration = MsUserFeignClientConfiguration.class*/)
public interface MsUserFeignClient {

    @GetMapping("/users/{userId}")
    UserDTO findById(@PathVariable("userId") Integer userId);
}
