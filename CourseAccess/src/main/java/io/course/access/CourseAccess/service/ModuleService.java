package io.course.access.CourseAccess.service;

import io.course.access.CourseAccess.model.entity.ModuleEntity;

import java.util.List;
import java.util.Optional;

public interface ModuleService {
    ModuleEntity saveModule(ModuleEntity module);

    Optional <ModuleEntity> findByTitle(String title);

    Optional <ModuleEntity> findByCourseIdAndTitle(String courseID, String title);

    Optional <ModuleEntity> findByCourseIdAndChapter(String courseID, Integer chapter);

    List <ModuleEntity> findModulesByCourseId(String courseId);

    List<ModuleEntity> findModulesByCourseName(String courseName);

    Boolean deleteModuleById(Integer moduleId);

    ModuleEntity updateModule(ModuleEntity module);
}
