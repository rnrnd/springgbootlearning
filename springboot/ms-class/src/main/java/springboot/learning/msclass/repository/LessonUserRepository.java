package springboot.learning.msclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.learning.msclass.domain.entity.LessonUser;

@Repository
public interface LessonUserRepository extends JpaRepository<LessonUser, Integer> {

    @Query("from LessonUser where leesonId=?1")
    LessonUser findByLessonId(Integer id);
    @Query("from LessonUser where leesonId=?1 and userId=?2")
    LessonUser findByLessonIdAndUserId(Integer lessonId, Integer userId);
}
