package io.course.access.CourseAccess.controller.student;


import io.course.access.CourseAccess.model.entity.CourseEntity;
import io.course.access.CourseAccess.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("StudentCourseController")
@RequestMapping("api/student/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

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
