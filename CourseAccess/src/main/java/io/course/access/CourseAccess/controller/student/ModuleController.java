package io.course.access.CourseAccess.controller.student;
import io.course.access.CourseAccess.model.entity.ModuleEntity;
import io.course.access.CourseAccess.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("StudentModuleController")
@RequestMapping("api/student/module")

public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @GetMapping("find/all/{courseId}")
    public ResponseEntity<?> findModulesByCourseName(@PathVariable String courseId) {
        List <ModuleEntity> modules = moduleService.findModulesByCourseId(courseId);
        return ResponseEntity.ok(modules);
    }

    @GetMapping("findByTitle/{course_id}")
    public ResponseEntity<?> findByTitle(@PathVariable String course_id, @RequestParam String title) {
        Optional<ModuleEntity> module = moduleService.findByCourseIdAndTitle(course_id, title);
        if (module.isEmpty()) return ResponseEntity.badRequest().body("Module not found");
        return ResponseEntity.ok(module.get());
    }
}
