package io.course.access.CourseAccess.repository;

import io.course.access.CourseAccess.model.entity.CourseEntity;
import io.course.access.CourseAccess.model.entity.dto.CourseRatingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import io.course.access.CourseAccess.model.entity.dto.CourseRatingDTO;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository <CourseEntity, String> {

    Optional<CourseEntity> findCoursesEntityByName(String courseName);

    @Query("SELECT c FROM CourseEntity c JOIN c.lecturer l WHERE l.id = :lecturerId")
    List<CourseEntity> findByLecturerId(@Param("lecturerId") Integer lecturerId);

    @Query("SELECT c FROM CourseEntity c JOIN c.lecturer l WHERE l.username = :lecturerName")
    List<CourseEntity> findByLecturerName(@Param("lecturerName") String lecturerName);

    @Modifying
    @Transactional
    @Query("UPDATE CourseEntity c SET c.enable = :enable WHERE c.name = :courseName")
    void updateCourseEnable(@Param("courseName") String courseName, @Param("enable") boolean enable);

    @Query("SELECT new io.course.access.CourseAccess.model.entity.dto.CourseRatingDTO(c.id, c.name, c.capacity, AVG(s.ratingScore)) " +
            "FROM CourseEntity c JOIN SubscriptionEntity s ON c.id = s.course.id " +
            "WHERE c.lecturer.id = :lecturerId " +
            "GROUP BY c.id, c.name, c.capacity")
    List<CourseRatingDTO> findCoursesWithAverageRatingByLecturerId(@Param("lecturerId") Integer lecturerId);
}






