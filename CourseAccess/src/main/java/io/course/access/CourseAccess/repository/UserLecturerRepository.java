package io.course.access.CourseAccess.repository;

import io.course.access.CourseAccess.model.entity.UserLecturerEntity;
import io.course.access.CourseAccess.model.entity.UserStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserLecturerRepository extends JpaRepository<UserLecturerEntity, Integer> {
    Optional<UserLecturerEntity> findByUsername(String username);

    Optional<UserLecturerEntity> findById(Integer id);

    @Query("select id from UserLecturerEntity where username = :username")
    Integer findIdByUsername(String username);



    @Modifying
    @Query("update UserLecturerEntity set enable = :enable where username = :username")
    void updateUserLecturerEnable(@Param("username") String username, @Param("enable") Boolean enable);
}