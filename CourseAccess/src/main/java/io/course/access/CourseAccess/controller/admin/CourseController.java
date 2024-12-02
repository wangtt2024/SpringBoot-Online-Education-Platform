package io.course.access.CourseAccess.controller.admin;


import io.course.access.CourseAccess.model.entity.CourseEntity;
import io.course.access.CourseAccess.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminCourseController")
@RequestMapping("api/admin/course")
public class CourseController {
    @Autowired
    private CourseService courseService;


    @PostMapping("create")
    public ResponseEntity<?> createCourse(@RequestBody CourseEntity course) {
        CourseEntity saved_course = courseService.saveCourse(course);
        return ResponseEntity.ok(saved_course);
    }

    @DeleteMapping("delete/{courseName}")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseName) {
        Boolean deleted = courseService.deleteCourse(courseName);
        return ResponseEntity.ok(deleted);
    }

    @PutMapping("update/name")
    public ResponseEntity<?> updateCourse(@RequestBody CourseEntity course) {
        courseService.updateCourse(course.getName());
        return ResponseEntity.ok("Course updated");
    }

    @PutMapping("update/enable")
    public ResponseEntity<?> updateCourseEnable(@RequestParam String courseName, @RequestParam boolean enable) {
        courseService.updateCourseEnable(courseName, enable);
        return ResponseEntity.ok("Course enable status updated");
    }

    @GetMapping("find/all")
    public ResponseEntity<?> findAllCourses() {
        List<CourseEntity> courses = courseService.findAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("find")
    public ResponseEntity<?> findCourse(@RequestParam String courseName) {
        CourseEntity course = courseService.findCourseByName(courseName).orElse(null);
        return ResponseEntity.ok(course);
    }

    @GetMapping("find/lecturer")
    public ResponseEntity<?> findCourseByLecturerName(@RequestParam String lecturerName) {
        List<CourseEntity> courses = courseService.findCoursesByLecturerName(lecturerName);
        return ResponseEntity.ok(courses);
    }



}
