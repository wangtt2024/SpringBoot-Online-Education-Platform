package io.course.access.CourseAccess.model.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "Students",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_pair", columnNames = {"username", "password"})
        }
)
public class UserStudentEntity extends UserEntity {
    @Column(name = "degree", columnDefinition = "VARCHAR(255) COMMENT 'Degree of program'")
    private String degree;
}
