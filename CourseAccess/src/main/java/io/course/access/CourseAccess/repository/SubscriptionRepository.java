package io.course.access.CourseAccess.repository;

import io.course.access.CourseAccess.model.entity.CourseEntity;
import io.course.access.CourseAccess.model.entity.SubscriptionEntity;
import io.course.access.CourseAccess.model.entity.UserStudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    @Query("SELECT s.studentComments FROM SubscriptionEntity s WHERE s.course = :course")
    List<String> findStudentCommentsByCourse(@Param("course") CourseEntity course);

    @Query("SELECT AVG(s.ratingScore) FROM SubscriptionEntity s WHERE s.course = :course")
    Float findOverallRatingByCourse(@Param("course") CourseEntity course);

    @Query("SELECT s FROM SubscriptionEntity s WHERE s.student = :student")
    List<SubscriptionEntity> findByStudent(@Param("student") UserStudentEntity student);

    @Query("SELECT s FROM SubscriptionEntity s WHERE s.course = :course")
    List<SubscriptionEntity> findByCourse(@Param("course") CourseEntity course);

    @Query("SELECT s FROM SubscriptionEntity s WHERE s.course = :course AND s.student = :student")
    Optional<SubscriptionEntity> findByCourseStudent(@Param("course") CourseEntity course,
                                                     @Param("student") UserStudentEntity student);

    @Modifying
    @Transactional
    @Query("DELETE FROM SubscriptionEntity s WHERE s.course = :course AND s.student = :student")
    void deleteByCourse(@Param("course") CourseEntity course,
                        @Param("student") UserStudentEntity student);
}


