package io.course.access.CourseAccess.service;

import io.course.access.CourseAccess.model.entity.SubscriptionEntity;
import io.course.access.CourseAccess.model.entity.dto.CourseRatingDTO;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    SubscriptionEntity saveSubscription(SubscriptionEntity subscription);
    SubscriptionEntity addSubscription(String courseId, Integer studentId);
    boolean deleteSubscription(Integer id);
    Optional<SubscriptionEntity> findSubscriptionById(Integer id);
    boolean deleteSubscriptionByCourseIdStudentId(String course_id, Integer student_id);
    List<SubscriptionEntity> findAllSubscriptions();
    List<SubscriptionEntity> findSubscriptionsByStudentId(Integer student_id);
    List<SubscriptionEntity> findSubscriptionsByCourseId(String course_id);
    SubscriptionEntity addRating(String course_id, Integer student_id, Integer ratingScore);
    SubscriptionEntity addComment(String course_id, Integer student_id, String comment);
    Float showOverallRating(String course_id);
    List<String> showAllComments(String course_id);

}