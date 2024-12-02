package io.course.access.CourseAccess.model.entity.dto;

public class CourseRatingDTO {
    private String courseId;
    private String courseName;
    private Integer courseCapacity;
    private Double averageRating;

    public CourseRatingDTO(String courseId, String courseName, Integer courseCapacity, Double averageRating) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCapacity = courseCapacity;
        this.averageRating = averageRating;}


        public String getCourseId () {
            return courseId;
        }

        public void setCourseId (String courseId){
            this.courseId = courseId;
        }

        public String getCourseName () {
            return courseName;
        }

        public void setCourseName (String courseName){
            this.courseName = courseName;
        }

        public Integer getCourseCapacity () {
            return courseCapacity;
        }

        public void setCourseCapacity (Integer courseCapacity){
            this.courseCapacity = courseCapacity;
        }

        public Double getAverageRating () {
            return averageRating;
        }

        public void setAverageRating (Double averageRating){
            this.averageRating = averageRating;
        }
    }

