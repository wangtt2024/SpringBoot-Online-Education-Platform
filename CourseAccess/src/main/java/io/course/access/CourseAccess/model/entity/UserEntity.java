package io.course.access.CourseAccess.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class UserEntity {
    @Id
    @Column(name = "id",
            columnDefinition = "INT NOT NULL COMMENT 'User ID'")
    private Integer id;

    @Column(name = "username",
            columnDefinition = "VARCHAR(31) NOT NULL UNIQUE COMMENT 'Username'")
    private String username;

    @Column(name = "password",
            columnDefinition = "VARCHAR(255) NOT NULL COMMENT 'Password'")
    private String password;

    @Column(name = "nickname",
            columnDefinition = "VARCHAR(31) DEFAULT '' COMMENT 'Nickname'")
    private String nickname;

    @Column(name = "enable",
            columnDefinition = "BOOLEAN DEFAULT FALSE COMMENT 'Is enabled?'")
    private Boolean enable = true;

    @Column(name = "update_time",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update time'")
    private LocalDateTime updateTime = LocalDateTime.now();

    @Column(name = "create_time",
            columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Create time'")
    private LocalDateTime createTime = LocalDateTime.now();
}
