package io.course.access.CourseAccess.controller.admin;

import io.course.access.CourseAccess.model.entity.SubscriptionEntity;
import io.course.access.CourseAccess.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminSubscriptionController")
@RequestMapping("api/admin/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("create")
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionEntity subscription) {
        SubscriptionEntity saved_subscription = subscriptionService.saveSubscription(subscription);
        return ResponseEntity.ok(saved_subscription);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteSubscription(@PathVariable Integer id) {
        Boolean deleted = subscriptionService.deleteSubscription(id);
        return ResponseEntity.ok(deleted);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<?> findSubscriptionById(@PathVariable Integer id) {
        SubscriptionEntity subscription = subscriptionService.findSubscriptionById(id).orElse(null);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping("find/all")
    public ResponseEntity<?> findAllSubscriptions() {
        List<SubscriptionEntity> subscriptions = subscriptionService.findAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("rating/{course_id}/score")
    public ResponseEntity<?> showRating(@PathVariable String course_id) {
        Float rating_score= subscriptionService.showOverallRating(course_id);
        return ResponseEntity.ok(rating_score);
    }

    @GetMapping("rating/{course_id}/comments")
    public ResponseEntity<?> showComments(@PathVariable String course_id) {
        List<String> rating_comments= subscriptionService.showAllComments(course_id);
        return ResponseEntity.ok(rating_comments);
    }
}