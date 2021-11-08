package springboot.learning.msclass.domain.entity;

import javax.persistence.*;

@Table(name = "lesson_user")
@Entity
public class LessonUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private Integer leesonId;
    @Column
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public Integer getLeesonId() {
        return leesonId;
    }

    public void setLeesonId(Integer leesonId) {
        this.leesonId = leesonId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
