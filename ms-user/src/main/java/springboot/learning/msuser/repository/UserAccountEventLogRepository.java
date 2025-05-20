package springboot.learning.msuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.learning.msuser.domain.entity.UserAccountEventLog;

@Repository
public interface UserAccountEventLogRepository extends JpaRepository<UserAccountEventLog, Integer> {
}
