package io.course.access.CourseAccess.repository;

import io.course.access.CourseAccess.model.entity.UserAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserAdminRepository  extends JpaRepository<UserAdminEntity, Integer> {
    UserAdminEntity findByUsername(String username);


}
