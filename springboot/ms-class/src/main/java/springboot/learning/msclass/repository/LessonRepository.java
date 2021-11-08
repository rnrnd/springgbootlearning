package springboot.learning.msclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.learning.msclass.domain.entity.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
