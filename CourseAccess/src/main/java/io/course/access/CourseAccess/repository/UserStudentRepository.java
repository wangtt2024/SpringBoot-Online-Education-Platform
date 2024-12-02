package io.course.access.CourseAccess.repository;

import io.course.access.CourseAccess.model.entity.UserStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserStudentRepository extends JpaRepository<UserStudentEntity, Integer> {
    Optional<UserStudentEntity> findByUsername(String username);

    @Query("select id from UserStudentEntity where username = :username")
    Integer findIdByUsername(String username);

    @Modifying
    @Query("update UserStudentEntity set enable = :enable where username = :username")
    void updateUserStudentEnable(@Param("username") String username, @Param("enable") Boolean enable);
}