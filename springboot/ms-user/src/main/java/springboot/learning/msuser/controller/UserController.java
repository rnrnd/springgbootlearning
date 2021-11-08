package springboot.learning.msuser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springboot.learning.msuser.domain.entity.User;
import springboot.learning.msuser.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{id}")
    public User findById(@PathVariable Integer id){
        /*User user = new User();
        user.setId(1);
        user.setMoney(BigDecimal.valueOf(1000));
        user.setPassword("111111");
        user.setUername("zhangsan");
        user.setRole("user");
        user.setRegTime(new Date());
        userService.save(user);*/
        return userService.findById(id);
    }

}
