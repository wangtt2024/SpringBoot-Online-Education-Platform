package io.course.access.CourseAccess.service;

import io.course.access.CourseAccess.model.entity.CourseEntity;
import io.course.access.CourseAccess.model.entity.dto.CourseRatingDTO;
import io.course.access.CourseAccess.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CourseEntity saveCourse(CourseEntity course) {
        return courseRepository.save(course);
    }

    @Override
    public boolean deleteCourse(String courseName) {
        Optional<CourseEntity> courseEntity = courseRepository.findCoursesEntityByName(courseName);
        if (courseEntity.isPresent()) {
            courseRepository.delete(courseEntity.get());
            return true;
        }
        return false;
    }

    @Override
    public void updateCourse(String courseName) {
        Optional<CourseEntity> courseEntity = courseRepository.findCoursesEntityByName(courseName);
        courseEntity.ifPresent(entity -> courseRepository.save(entity));
    }

    @Override
    public Optional<CourseEntity> findCourseById(String courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public Optional<CourseEntity> findCourseByName(String courseName) {
        return courseRepository.findCoursesEntityByName(courseName);
    }

    @Override
    public List<CourseEntity> findCoursesByLecturerId(Integer lecturerId) {
        return courseRepository.findByLecturerId(lecturerId);
    }

    @Override
    public List<CourseEntity> findCoursesByLecturerName(String lecturerName) {
        return courseRepository.findByLecturerName(lecturerName);
    }

    @Override
    public List<CourseEntity> findAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void updateCourseEnable(String courseName, boolean enable) {
        courseRepository.updateCourseEnable(courseName, enable);
    }

    @Override
    public List<CourseRatingDTO> findCoursesWithAverageRatingByLecturerId(Integer lecturerId) {
        return courseRepository.findCoursesWithAverageRatingByLecturerId(lecturerId);
    }

}
