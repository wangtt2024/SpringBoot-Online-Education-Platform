package io.course.access.CourseAccess.model.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Lecturers",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_pair", columnNames = {"username", "password"})
        }
)
public class UserLecturerEntity extends UserEntity {
    @Column(name = "specialization", columnDefinition = "VARCHAR(255) COMMENT 'Specialization'")
    private String specialization;
}
