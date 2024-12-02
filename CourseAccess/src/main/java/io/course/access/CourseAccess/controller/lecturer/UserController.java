package io.course.access.CourseAccess.controller.lecturer;

import io.course.access.CourseAccess.model.entity.UserLecturerEntity;
import io.course.access.CourseAccess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("LecturerUserController")
@RequestMapping("api/lecturer/user")//pre-path
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping("register")//api/user/register/lecturer
    public ResponseEntity<?> registerLecturer(@RequestBody UserLecturerEntity lecturer)
    {
        UserLecturerEntity saved_lecturer = userService.saveLecturer(lecturer);

        return ResponseEntity.ok(saved_lecturer);
    }

    @DeleteMapping("delete/{username}")//api/user/delete/lecturer/{username}
    public ResponseEntity<?> deleteLecturer(@PathVariable String username)
    {
        Boolean deleted = userService.deleteLecturerByUsername(username);
        if (deleted)
        {
            return ResponseEntity.ok("Lecturer deleted");
        }else{

            return ResponseEntity.ok("Lecturer not found");
        }
    }

    @PutMapping("update")//api/user/update/lecturer
    public ResponseEntity<?> updateLecturer(@RequestBody UserLecturerEntity user)
    {
        UserLecturerEntity updated_lecturer = userService.updateLecturer(user);

        return ResponseEntity.ok(updated_lecturer);
    }

    @GetMapping("find/{username}")//api/user/find/lecturer/{username}
    public ResponseEntity<?> findLecturer(@PathVariable String username)
    {
        UserLecturerEntity lecturer = userService.findLecturerByUsername(username).orElse(null);

        return ResponseEntity.ok(lecturer);
    }

    @GetMapping ("find/id/{username}")//api/user/find/lecturer/id/{username}
    public ResponseEntity<?> findLecturerId(@PathVariable String username)
    {
        Integer lecturer_id = userService.findLecturerIdByUsername(username).orElse(null);
        if (lecturer_id == null) return ResponseEntity.badRequest().body("Lecturer username not found");
        return ResponseEntity.ok(lecturer_id);
    }
}