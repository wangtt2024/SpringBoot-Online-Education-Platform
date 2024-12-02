package io.course.access.CourseAccess.controller.lecturer;


import io.course.access.CourseAccess.model.entity.CourseEntity;
import io.course.access.CourseAccess.model.entity.dto.CourseRatingDTO;
import io.course.access.CourseAccess.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("LecturerCourseController")
@RequestMapping("api/lecturer/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PutMapping("update/name")
    public ResponseEntity<?> updateCourse(@RequestBody CourseEntity course, @RequestParam Integer lecturer_id) {
        Optional<CourseEntity> courseEntity = courseService.findCourseById(course.getId());
        if (courseEntity.isEmpty()) return ResponseEntity.badRequest().body("Course not found");
        if (!courseEntity.get().getLecturer().getId().equals(lecturer_id)) return ResponseEntity.badRequest().body("Unauthorized");
        courseService.saveCourse(course);
        return ResponseEntity.ok("Course updated");
    }

    @GetMapping("find/all")
    public ResponseEntity<?> findAllMyCourses(@RequestParam Integer lecturer_id) {
        List<CourseEntity> courses = courseService.findCoursesByLecturerId(lecturer_id);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("find/course_name/{courseName}")
    public ResponseEntity<?> findCourse(@PathVariable String courseName) {
        Optional<CourseEntity> course = courseService.findCourseByName(courseName);
        if (course.isEmpty()) return ResponseEntity.badRequest().body("Course not found");
        return ResponseEntity.ok(course);
    }

    @GetMapping("find/course_id/{course_id}")
    public ResponseEntity<?> findCourseById(@PathVariable String course_id) {
        Optional<CourseEntity> course = courseService.findCourseById(course_id);
        if (course.isEmpty()) return ResponseEntity.badRequest().body("Course not found");
        return ResponseEntity.ok(course);
    }

    @GetMapping("find/average-rating")
    public ResponseEntity<?> findCoursesWithAverageRatingByLecturerId(@RequestParam Integer lecturerId) {
        List<CourseRatingDTO> courses = courseService.findCoursesWithAverageRatingByLecturerId(lecturerId);
        return ResponseEntity.ok(courses);
    }
}
