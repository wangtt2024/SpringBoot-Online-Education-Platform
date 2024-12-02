package io.course.access.CourseAccess.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Subscriptions",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_pair", columnNames = {"student_id", "course_id"})
        }
)
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT NOT NULL AUTO_INCREMENT COMMENT 'Subscription ID'")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id", columnDefinition = "INT NOT NULL COMMENT 'Student ID'", referencedColumnName = "id")
    private UserStudentEntity student;

    @ManyToOne
    @JoinColumn(name = "course_id", columnDefinition = "VARCHAR(31) NOT NULL COMMENT 'Course ID'", referencedColumnName = "id")
    private CourseEntity course;

    @Column(name = "subscription_date", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Subscription Date'")
    private LocalDateTime subscriptionDate = LocalDateTime.now();

    @Column(name = "rating_score", columnDefinition = "INT COMMENT 'Rating Score'")
    private Integer ratingScore;

    @Column(name = "student_comments", columnDefinition = "TEXT COMMENT 'Student Comments'")
    private String studentComments;
}