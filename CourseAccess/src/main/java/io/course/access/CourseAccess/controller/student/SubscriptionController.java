package io.course.access.CourseAccess.controller.student;

import io.course.access.CourseAccess.model.entity.SubscriptionEntity;
import io.course.access.CourseAccess.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("StudentSubscriptionController")
@RequestMapping("api/student/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("add/{course_id}")
    public ResponseEntity<?> addSubscription(@PathVariable String course_id, @RequestParam Integer student_id) {
        SubscriptionEntity subscription = subscriptionService.addSubscription(course_id, student_id);
        return ResponseEntity.ok(subscription);
    }

    @DeleteMapping("delete/{course_id}")
    public ResponseEntity<?> deleteSubscriptionByCourseId(@PathVariable String course_id,
                                                          @RequestParam Integer student_id) {
        Boolean deleted = subscriptionService.deleteSubscriptionByCourseIdStudentId(course_id, student_id);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("find/all")
    public ResponseEntity<?> findAllMySubscriptions(@RequestParam Integer student_id) {
        List<SubscriptionEntity> subscriptions = subscriptionService.findSubscriptionsByStudentId(student_id);
        return ResponseEntity.ok(subscriptions);
    }

    @PostMapping("rate/{course_id}")
    public ResponseEntity<?> addRating(@PathVariable String course_id , @RequestParam Integer student_id,
                                       @RequestParam Integer ratingScore) {
        SubscriptionEntity updated_subscription = subscriptionService.addRating(course_id, student_id, ratingScore);
        if (updated_subscription == null) return ResponseEntity.badRequest().body("Unsuccessful rating");
        return ResponseEntity.ok(updated_subscription);
    }

    @PostMapping("comment/{course_id}")
    public ResponseEntity<?> addComment(@PathVariable String course_id , @RequestParam Integer student_id,
                                       @RequestParam String ratingComment) {
        SubscriptionEntity updated_subscription = subscriptionService.addComment(course_id, student_id, ratingComment);
        if (updated_subscription == null) return ResponseEntity.badRequest().body("Unsuccessful comment");
        return ResponseEntity.ok(updated_subscription);
    }


}