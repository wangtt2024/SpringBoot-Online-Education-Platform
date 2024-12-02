package io.course.access.CourseAccess.controller.admin;
import io.course.access.CourseAccess.model.entity.ModuleEntity;
import io.course.access.CourseAccess.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("AdminModuleController")
@RequestMapping("api/admin/module")

public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @GetMapping("findByCourseName")
    public ResponseEntity<?> findModulesByCourseName(@RequestParam String courseName) {
        List<ModuleEntity> modules = moduleService.findModulesByCourseName(courseName);
        return ResponseEntity.ok(modules);
    }

    @GetMapping("findByTitle")
    public ResponseEntity<?> findByTitle(@RequestParam String title) {
        Optional<ModuleEntity> module = moduleService.findByTitle(title);
        return ResponseEntity.ok(module.orElse(null));
    }
}
