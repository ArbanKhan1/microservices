package com.auth.controller;

import com.auth.dto.AddRolesRequest;
import com.auth.dto.LoginDto;
import com.auth.dto.UserRequest;
import com.auth.dto.UserResponse;
import com.auth.model.User;
import com.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/add-role")
    public ResponseEntity<List<String>> addRoles(@RequestBody AddRolesRequest roles) {
        return ResponseEntity.ok(authService.addRole(roles));
    }

    @PostMapping("/add-user")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(authService.createUser(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<String> addUser(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("HELLO ADMIN");
    }

    @GetMapping("/teacher")
    public ResponseEntity<String> teacher() {
        return ResponseEntity.ok("HELLO TEACHER");
    }

    @GetMapping("/both")
    public ResponseEntity<String> both() {
        return ResponseEntity.ok("HELLO");
    }

}
