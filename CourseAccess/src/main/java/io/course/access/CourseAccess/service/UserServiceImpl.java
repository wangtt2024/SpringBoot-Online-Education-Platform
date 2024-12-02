package io.course.access.CourseAccess.service;


import io.course.access.CourseAccess.model.entity.UserLecturerEntity;
import io.course.access.CourseAccess.model.entity.UserStudentEntity;
import io.course.access.CourseAccess.repository.UserAdminRepository;
import io.course.access.CourseAccess.repository.UserLecturerRepository;
import io.course.access.CourseAccess.repository.UserStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserLecturerRepository userLecturerRepository;

    @Autowired
    private UserStudentRepository userStudentRepository;

    @Autowired
    private UserAdminRepository userAdminRepository;



    @Override
    public UserLecturerEntity saveLecturer(UserLecturerEntity lecturer)
    {
        return userLecturerRepository.save(lecturer);
    }

    @Override
    public UserStudentEntity saveStudent(UserStudentEntity student)
    {
        return userStudentRepository.save(student);
    }

    @Override
    public Boolean deleteLecturerByUsername(String username)
    {
        Optional<UserLecturerEntity> lecturer = userLecturerRepository.findByUsername(username);
        if (lecturer.isPresent())
        {
            userLecturerRepository.delete(lecturer.get());
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteStudentByUsername(String username)
    {
        Optional<UserStudentEntity> student = userStudentRepository.findByUsername(username);
        if (student.isPresent())
        {
            userStudentRepository.delete(student.get());
            return true;
        }
        return false;
    }

    @Override
    public UserLecturerEntity updateLecturer(UserLecturerEntity lecturer)
    {
        return userLecturerRepository.save(lecturer);
    }

    @Override
    public UserStudentEntity updateStudent(UserStudentEntity student)
    {
        return userStudentRepository.save(student);
    }

    @Override
    public Optional<UserLecturerEntity> findLecturerByUsername(String username)
    {
        return userLecturerRepository.findByUsername(username);
    }

    @Override
    public Optional<UserStudentEntity> findStudentByUsername(String username)
    {
        return userStudentRepository.findByUsername(username);
    }

    @Override
    public Optional<Integer> findStudentIdByUsername(String username)
    {
        return Optional.ofNullable(userStudentRepository.findIdByUsername(username));
    }

    @Override
    public Optional<Integer> findLecturerIdByUsername(String username)
    {
        return Optional.ofNullable(userLecturerRepository.findIdByUsername(username));
    }



}
