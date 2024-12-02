package io.course.access.CourseAccess.service;

import io.course.access.CourseAccess.model.entity.CourseEntity;
import io.course.access.CourseAccess.model.entity.UserStudentEntity;
import io.course.access.CourseAccess.model.entity.dto.CourseRatingDTO;
import io.course.access.CourseAccess.repository.CourseRepository;
import io.course.access.CourseAccess.model.entity.SubscriptionEntity;
import io.course.access.CourseAccess.repository.SubscriptionRepository;
import io.course.access.CourseAccess.repository.UserStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserStudentRepository userStudentRepository;

    @Override
    public SubscriptionEntity saveSubscription(SubscriptionEntity subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public SubscriptionEntity addSubscription(String courseId, Integer studentId) {
        if (courseRepository.existsById(courseId) && userStudentRepository.existsById(studentId)) {
            SubscriptionEntity subscription = new SubscriptionEntity();
            CourseEntity course = courseRepository.findById(courseId).orElse(null);
            UserStudentEntity student = userStudentRepository.findById(studentId).orElse(null);
            subscription.setCourse(course);
            subscription.setStudent(student);
            return subscriptionRepository.save(subscription);
        }
        return null;
    }

    @Override
    public boolean deleteSubscription(Integer id) {
        if (subscriptionRepository.existsById(id)) {
            subscriptionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSubscriptionByCourseIdStudentId(String course_id, Integer student_id) {
        Optional<CourseEntity> course = courseRepository.findById(course_id);
        if (course.isEmpty()) return false;
        Optional<UserStudentEntity> student = userStudentRepository.findById(student_id);
        if (student.isEmpty()) return false;
        subscriptionRepository.deleteByCourse(course.get(), student.get());
        return true;
    }

    @Override
    public List<SubscriptionEntity> findAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Optional<SubscriptionEntity> findSubscriptionById(Integer id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public List<SubscriptionEntity> findSubscriptionsByStudentId(Integer student_id) {
        Optional<UserStudentEntity> student = userStudentRepository.findById(student_id);
        if (student.isEmpty()) return new ArrayList<>();
        return subscriptionRepository.findByStudent(student.get());
    }

    @Override
    public List<SubscriptionEntity> findSubscriptionsByCourseId(String course_id) {
        Optional<CourseEntity> course = courseRepository.findById(course_id);
        if (course.isEmpty()) return new ArrayList<>();
        return subscriptionRepository.findByCourse(course.get());
    }

    @Override
    public SubscriptionEntity addRating(String course_id, Integer student_id, Integer ratingScore) {
        Optional<CourseEntity> courseOpt = courseRepository.findById(course_id);
        Optional<UserStudentEntity> studentOpt = userStudentRepository.findById(student_id);
        if (courseOpt.isEmpty() || studentOpt.isEmpty()) return null;
        Optional<SubscriptionEntity> subscriptionOpt = subscriptionRepository.findByCourseStudent(courseOpt.get(), studentOpt.get());
        if (subscriptionOpt.isPresent()) {
            SubscriptionEntity subscription = subscriptionOpt.get();
            if (subscription.getRatingScore() != null && subscription.getStudentComments() != null) return null;
            if (subscription.getRatingScore() == null) {
                subscription.setRatingScore(ratingScore);
            }
            return subscriptionRepository.save(subscription);
        }
        return null;
    }

    @Override
    public SubscriptionEntity addComment(String course_id, Integer student_id, String comment) {
        Optional<CourseEntity> courseOpt = courseRepository.findById(course_id);
        Optional<UserStudentEntity> studentOpt = userStudentRepository.findById(student_id);
        if (courseOpt.isEmpty() || studentOpt.isEmpty()) return null;
        Optional<SubscriptionEntity> subscriptionOpt = subscriptionRepository.findByCourseStudent(courseOpt.get(), studentOpt.get());
        if (subscriptionOpt.isPresent()) {
            SubscriptionEntity subscription = subscriptionOpt.get();
            if (subscription.getRatingScore() != null && subscription.getStudentComments() != null) return null;
            if (subscription.getStudentComments() == null) {
                subscription.setStudentComments(comment);
            }
            return subscriptionRepository.save(subscription);
        }
        return null;
    }



    @Override
    public Float showOverallRating(String course_id) {
        Optional<CourseEntity> course = courseRepository.findById(course_id);
        return course.map(courseEntity -> subscriptionRepository.findOverallRatingByCourse(courseEntity)).orElse(null);
    }

    @Override
    public List<String> showAllComments(String course_id) {
        Optional<CourseEntity> course = courseRepository.findById(course_id);
        if (course.isEmpty()) return new ArrayList<>();
        return subscriptionRepository.findStudentCommentsByCourse(course.get());
    }





}