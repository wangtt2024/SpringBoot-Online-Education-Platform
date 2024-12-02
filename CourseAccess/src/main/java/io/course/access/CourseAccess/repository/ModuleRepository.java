package io.course.access.CourseAccess.repository;
import io.course.access.CourseAccess.model.entity.CourseEntity;
import io.course.access.CourseAccess.model.entity.ModuleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Integer> {
    Optional<ModuleEntity> findModuleEntityByTitle(String title);

    @Query("SELECT m FROM ModuleEntity m JOIN m.course c WHERE c = :course AND m.title = :title")
    Optional<ModuleEntity> findModuleEntityByCourseTitle(@Param("course") CourseEntity course,
                                                         @Param("title") String title);

    @Query("SELECT m FROM ModuleEntity m JOIN m.course c WHERE c = :course AND m.chapter = :chapter")
    Optional<ModuleEntity> findModuleEntityByCourseChapter(@Param("course") CourseEntity course,
                                                           @Param("chapter") Integer chapter);

    @Query("SELECT m FROM ModuleEntity m JOIN m.course c WHERE c.name = :courseName ORDER BY m.chapter ASC")
    List<ModuleEntity> findByCourseName(String courseName);

    @Query("SELECT m FROM ModuleEntity m JOIN m.course c WHERE c.id = :courseId")
    List <ModuleEntity> findModulesByCourseId(@Param("courseId") String courseId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ModuleEntity m WHERE m.id = :moduleId")
    void deleteModuleById(@Param("moduleId") Integer moduleId);
}
