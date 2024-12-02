package io.course.access.CourseAccess.controller.lecturer;
import io.course.access.CourseAccess.model.entity.ModuleEntity;
import io.course.access.CourseAccess.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("LecturerModuleController")
@RequestMapping("api/lecturer/module")
public class ModuleController {
    @Autowired
    private ModuleService moduleService;

    @PostMapping("create")
    public ResponseEntity<?> createModule(@RequestBody ModuleEntity module, @RequestParam Integer lecturer_id) {
        if (!module.getCourse().getLecturer().getId().equals(lecturer_id)) return ResponseEntity.badRequest().body("Unauthorized");
        ModuleEntity saved_module = moduleService.saveModule(module);
        return ResponseEntity.ok(saved_module);
    }

    @DeleteMapping("delete/{course_id}/title/{title}")
    public ResponseEntity<?> deleteModuleByTitle(@PathVariable String course_id,
                                          @PathVariable String title,
                                          @RequestParam Integer lecturer_id) {
        Optional<ModuleEntity> module = moduleService.findByCourseIdAndTitle(course_id, title);
        return getResponseEntity(lecturer_id, module);
    }

    @DeleteMapping("delete/{course_id}/chapter/{chapter}")
    public ResponseEntity<?> deleteModuleByChapter(@PathVariable String course_id,
                                              @PathVariable Integer chapter,
                                              @RequestParam Integer lecturer_id) {
        Optional<ModuleEntity> module = moduleService.findByCourseIdAndChapter(course_id, chapter);
        return getResponseEntity(lecturer_id, module);
    }

    private ResponseEntity<?> getResponseEntity(@RequestParam Integer lecturer_id, Optional<ModuleEntity> module) {
        if (module.isEmpty()) return ResponseEntity.badRequest().body("Module not found");
        if (!module.get().getCourse().getLecturer().getId().equals(lecturer_id)) {
            return ResponseEntity.badRequest().body("Unauthorized");
        }

        Boolean deleted = moduleService.deleteModuleById(module.get().getId());
        return ResponseEntity.ok(deleted);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateModule(@RequestBody ModuleEntity module) {
        ModuleEntity updated_module = moduleService.updateModule(module);
        return ResponseEntity.ok(updated_module);
    }

    @GetMapping("find/all/{courseId}")
    public ResponseEntity<?> findModulesByCourseId(@PathVariable String courseId) {
        List<ModuleEntity> modules = moduleService.findModulesByCourseId(courseId);
        return ResponseEntity.ok(modules);
    }

    @GetMapping("findByTitle/{course_id}")
    public ResponseEntity<?> findByTitle(@PathVariable String course_id, @RequestParam String title) {
        Optional<ModuleEntity> module = moduleService.findByCourseIdAndTitle(course_id, title);
        if (module.isEmpty()) return ResponseEntity.badRequest().body("Module not found");
        return ResponseEntity.ok(module.get());
    }
}
