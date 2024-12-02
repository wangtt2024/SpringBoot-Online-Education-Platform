package io.course.access.CourseAccess.service;


import io.course.access.CourseAccess.model.entity.UserLecturerEntity;
import io.course.access.CourseAccess.model.entity.UserStudentEntity;

import java.util.Optional;

public interface UserService
{
    UserLecturerEntity saveLecturer(UserLecturerEntity lecturer);

    UserStudentEntity saveStudent(UserStudentEntity student);

    Boolean deleteLecturerByUsername(String username);

    Boolean deleteStudentByUsername(String username);

    UserLecturerEntity updateLecturer(UserLecturerEntity lecturer);

    UserStudentEntity updateStudent(UserStudentEntity student);

    Optional<UserLecturerEntity> findLecturerByUsername(String username);

    Optional<UserStudentEntity> findStudentByUsername(String username);

    Optional <Integer> findStudentIdByUsername(String username);

    Optional <Integer> findLecturerIdByUsername(String username);

}
