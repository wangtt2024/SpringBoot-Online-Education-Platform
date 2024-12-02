package io.course.access.CourseAccess.model.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Courses")
public class CourseEntity {
    @Id
    @Column(name = "id",
            columnDefinition = "VARCHAR(31) NOT NULL COMMENT 'Course ID'")
    private String id;

    @Column(name = "name",
            columnDefinition = "VARCHAR(255) NOT NULL COMMENT 'Course name'")
    private String name;

    @Column(name = "description",
            columnDefinition = "TEXT COMMENT 'Course description'")
    private String description;

    @ManyToOne
    @JoinColumn(name = "lecturer_id",
                columnDefinition = "INT NOT NULL COMMENT 'Lecturer ID'", referencedColumnName = "id")
    private UserLecturerEntity lecturer;

    @Column(name = "enable", columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE COMMENT 'Course enable status'")
    private Boolean enable = false;

    @Column(name = "capacity", columnDefinition = "INT NOT NULL COMMENT 'Course capacity'")
    private Integer capacity;
}
