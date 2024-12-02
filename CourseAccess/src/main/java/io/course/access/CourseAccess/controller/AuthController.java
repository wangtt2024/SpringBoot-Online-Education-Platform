package io.course.access.CourseAccess.controller;

import io.course.access.CourseAccess.model.entity.UserLecturerEntity;
import io.course.access.CourseAccess.model.entity.UserStudentEntity;
import io.course.access.CourseAccess.service.UserService;
import io.course.access.CourseAccess.security.JwtUtils;
import io.course.access.CourseAccess.security.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.role.equals("STUDENT")) {
            Optional<UserStudentEntity> student = userService.findStudentByUsername(loginRequest.username);
            if (student.isEmpty()) return ResponseEntity.badRequest().body("Invalid username or password");
            if (!student.get().getPassword().equals(loginRequest.password)) return ResponseEntity.badRequest().body("Invalid username or password");
            String token = jwtUtils.generateToken(student.get().getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        } else if (loginRequest.role.equals("LECTURER")) {
            Optional<UserLecturerEntity> lecturer = userService.findLecturerByUsername(loginRequest.username);
            if (lecturer.isEmpty()) return ResponseEntity.badRequest().body("Invalid username or password");
            if (!lecturer.get().getPassword().equals(loginRequest.password)) return ResponseEntity.badRequest().body("Invalid username or password");
            String token = jwtUtils.generateToken(lecturer.get().getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        } else if (loginRequest.role.equals("ADMIN")) {
            System.out.println(loginRequest.username);
            System.out.println(loginRequest.password);
            System.out.println(loginRequest.role);
            if (loginRequest.username.equals("admin") && loginRequest.password.equals("admin")) {
                String token = jwtUtils.generateToken("admin");
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                return ResponseEntity.badRequest().body("Invalid username or password");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        
    }
}