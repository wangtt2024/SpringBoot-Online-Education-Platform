package io.course.access.CourseAccess.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table (name = "Admins",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_pair", columnNames = {"username", "password"})
        }
)

public class UserAdminEntity extends UserEntity {
}
