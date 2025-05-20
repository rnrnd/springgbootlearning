package springboot.learning.msuser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.learning.msuser.domain.entity.User;
import springboot.learning.msuser.domain.dto.UserMoneyDTO;
import springboot.learning.msuser.domain.entity.UserAccountEventLog;
import springboot.learning.msuser.repository.UserAccountEventLogRepository;
import springboot.learning.msuser.repository.UserRepository;

import java.util.Date;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAccountEventLogRepository userAccountEventLogRepository;

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("user not exist!"));
    }

    public void save(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw e;
        }
    }

    public void lessonBuy(UserMoneyDTO moneyDTO) {
        //扣减金额
        User user = this.findById(moneyDTO.getUserId());
        user.setMoney(user.getMoney().subtract(moneyDTO.getMoney()));
        this.save(user);
        //记录日志
        this.userAccountEventLogRepository.save(
                new UserAccountEventLog(
                        moneyDTO.getUserId(),
                        moneyDTO.getMoney(),
                        moneyDTO.getEvent(),
                        moneyDTO.getDescription(),
                        new Date()
                )
        );
    }
}
