package io.course.access.CourseAccess.service;

import io.course.access.CourseAccess.model.entity.CourseEntity;
import io.course.access.CourseAccess.model.entity.ModuleEntity;
import io.course.access.CourseAccess.repository.ModuleRepository;
import io.course.access.CourseAccess.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    public ModuleEntity saveModule(ModuleEntity module) {
        return moduleRepository.save(module);
    }

    public Optional<ModuleEntity> findByTitle(String title) {
        return moduleRepository.findModuleEntityByTitle(title);
    }

    public Optional<ModuleEntity> findByCourseIdAndTitle(String course_id, String title) {
        Optional<CourseEntity> course_opt = courseRepository.findById(course_id);
        if (course_opt.isEmpty()) return Optional.empty();
        return moduleRepository.findModuleEntityByCourseTitle(course_opt.get(), title);
    }

    public Optional<ModuleEntity> findByCourseIdAndChapter(String course_id, Integer chapter) {
        Optional<CourseEntity> course_opt = courseRepository.findById(course_id);
        if (course_opt.isEmpty()) return Optional.empty();
        return moduleRepository.findModuleEntityByCourseChapter(course_opt.get(), chapter);
    }

    public List<ModuleEntity> findModulesByCourseName(String courseName) {
        return moduleRepository.findByCourseName(courseName);
    }

    public Boolean deleteModuleById(Integer moduleId) {
        moduleRepository.deleteModuleById(moduleId);
        return true;
    }

    public ModuleEntity updateModule(ModuleEntity module) {
        return moduleRepository.save(module);
    }

    public List<ModuleEntity> findModulesByCourseId(String courseId) {
        return moduleRepository.findModulesByCourseId(courseId);
    }
}
