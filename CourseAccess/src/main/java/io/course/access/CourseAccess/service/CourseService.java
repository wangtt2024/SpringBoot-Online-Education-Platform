package io.course.access.CourseAccess.service;

import io.course.access.CourseAccess.model.entity.CourseEntity;
import io.course.access.CourseAccess.model.entity.dto.CourseRatingDTO;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    CourseEntity saveCourse(CourseEntity course);

    boolean deleteCourse(String courseName);

    void updateCourse(String courseName);

    Optional<CourseEntity> findCourseById(String courseId);

    Optional<CourseEntity> findCourseByName(String courseName);

    List<CourseEntity> findCoursesByLecturerId(Integer lecturerId);

    List<CourseEntity> findCoursesByLecturerName(String lecturerName);

    List<CourseEntity> findAllCourses();

    void updateCourseEnable(String courseName, boolean enable);

    List<CourseRatingDTO> findCoursesWithAverageRatingByLecturerId(Integer lecturerId);



}