package io.course.access.CourseAccess.model.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Modules",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_module", columnNames = {"title", "course_id", "course_id"})
        }
)
public class ModuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT NOT NULL COMMENT 'Module ID'")
    private Integer id;

    @Column(name = "chapter", columnDefinition = "INT NOT NULL COMMENT 'Chapter number'")
    private Integer chapter;

    @Column(name = "title", columnDefinition = "VARCHAR(255) NOT NULL COMMENT 'Module title'")
    private String title;

    @Column(name = "summary", columnDefinition = "TEXT COMMENT 'Module description'")
    private String summary;

    @ManyToOne
    @JoinColumn(name = "course_id", columnDefinition = "VARCHAR(31) NOT NULL COMMENT 'Course ID'", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CourseEntity course;

    @Column(name = "last_update", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update time'")
    private LocalDateTime lastUpdate = LocalDateTime.now();

}
