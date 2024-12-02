package io.course.access.CourseAccess.controller.student;

import io.course.access.CourseAccess.model.entity.UserStudentEntity;
import io.course.access.CourseAccess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController("StudentUserController")
@RequestMapping("api/student/user")//pre-path
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("register")//api/user/register
    public ResponseEntity<?> registerStudent(@RequestBody UserStudentEntity student)
    {
        UserStudentEntity saved_student = userService.saveStudent(student);

        return ResponseEntity.ok(saved_student);
    }

    @DeleteMapping("delete/{username}")//api/user/delete/student/{username}
    public ResponseEntity<?> deleteStudent(@PathVariable String username)
    {
        Boolean deleted = userService.deleteStudentByUsername(username);
        if (deleted){
            return ResponseEntity.ok("Student deleted");
        } else{
            return ResponseEntity.ok("Student not found");
        }
    }

    @PutMapping("update")//api/user/update/student
    public ResponseEntity<?> updateStudent(@RequestBody UserStudentEntity user)
    {
        UserStudentEntity updated_student = userService.updateStudent(user);

        return ResponseEntity.ok(updated_student);
    }

    @GetMapping("find/{username}")//api/user/find/student/{username}
    public ResponseEntity<?> findStudent(@PathVariable String username)
    {
        UserStudentEntity student = userService.findStudentByUsername(username).orElse(null);

        return ResponseEntity.ok(student);
    }

    @GetMapping("find/id/{username}")//api/user/find/student/id/{username}
    public ResponseEntity<?> findStudentId(@PathVariable String username)
    {
        Optional<Integer> student_id = userService.findStudentIdByUsername(username);
        if (student_id.isEmpty()) return ResponseEntity.badRequest().body("Student username not found");
        return ResponseEntity.ok(student_id.get());
    }

}