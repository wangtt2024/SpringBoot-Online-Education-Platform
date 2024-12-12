package io.course.access.CourseAccess.controller.admin;

import io.course.access.CourseAccess.model.entity.UserLecturerEntity;
import io.course.access.CourseAccess.model.entity.UserStudentEntity;
import io.course.access.CourseAccess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("AdminUserController")
@RequestMapping("api/admin/user")//pre-path
public class UserController
{
    @Autowired
    private UserService userService;

    //checked
    @PostMapping("register/lecturer")//api/user/register/lecturer
    public ResponseEntity<?> registerLecturer(@RequestBody UserLecturerEntity lecturer)
    {
        UserLecturerEntity saved_lecturer = userService.saveLecturer(lecturer);

        return ResponseEntity.ok(saved_lecturer);
    }
    //checked
    @PostMapping("register/student")//api/user/register/student
    public ResponseEntity<?> registerStudent(@RequestBody UserStudentEntity student)
    {
        UserStudentEntity saved_student = userService.saveStudent(student);

        return ResponseEntity.ok(saved_student);
    }

    //checked
    @DeleteMapping("delete/lecturer-by-username/{username}")//api/user/delete/lecturer/{username}
    public ResponseEntity<?> deleteLecturer(@PathVariable String username)
    {
        Boolean deleted = userService.deleteLecturerByUsername(username);

        return ResponseEntity.ok(deleted);
    }

    @DeleteMapping("delete/lecturer-by-id/{id}")//api/user/delete/lecturer/{id}
    public ResponseEntity<?> deleteLecturerById(@PathVariable Integer id)
    {
        Boolean deleted = userService.deleteLecturerById(id);

        return ResponseEntity.ok(deleted);
    }

    //checked
    @DeleteMapping("delete/student-by_username/{username}")//api/user/delete/student/{username}
    public ResponseEntity<?> deleteStudent(@PathVariable String username)
    {
        Boolean deleted = userService.deleteStudentByUsername(username);
        if (deleted){
            return ResponseEntity.ok("User deleted");
        } else{
            return ResponseEntity.ok("User not found");
        }
    }

    @DeleteMapping("delete/student-by-id/{id}")//api/user/delete/student/{id}
    public ResponseEntity<?> deleteStudentById(@PathVariable Integer id)
    {
        Boolean deleted = userService.deleteStudentById(id);
        if (deleted){
            return ResponseEntity.ok("User deleted");
        } else{
            return ResponseEntity.ok("User not found");
        }
    }

    //checked
    @PutMapping("update/lecturer")//api/user/update/lecturer
    public ResponseEntity<?> updateLecturer(@RequestBody UserLecturerEntity user) {
        try {
            UserLecturerEntity updated_lecturer = userService.updateLecturer(user);
            return ResponseEntity.ok(updated_lecturer);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("username already exists");
        }
    }





    //checked
    @PutMapping("update/student")//api/user/update/student
    public ResponseEntity<?> updateStudent(@RequestBody UserStudentEntity user)
    {
        try {
            UserStudentEntity updated_student = userService.updateStudent(user);
            return ResponseEntity.ok(updated_student);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("Duplicated entry of user name");
        }
    }

    @GetMapping("find/lecturer/{username}")//api/user/find/lecturer/{username}
    public ResponseEntity<?> findLecturer(@PathVariable String username)
    {
        UserLecturerEntity lecturer = userService.findLecturerByUsername(username).orElse(null);

        return ResponseEntity.ok(lecturer);
    }

    @GetMapping("find/student/{username}")//api/user/find/student/{username}
    public ResponseEntity<?> findStudent(@PathVariable String username)
    {
        UserStudentEntity student = userService.findStudentByUsername(username).orElse(null);

        return ResponseEntity.ok(student);
    }

}