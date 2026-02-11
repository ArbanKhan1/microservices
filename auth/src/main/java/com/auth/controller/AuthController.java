package com.auth.controller;

import com.auth.dto.*;
import com.auth.model.User;
import com.auth.repository.UserRepository;
import com.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

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

    @GetMapping("/get-user")
    public ClientDto both(@RequestParam("email") String email) {
        System.out.print("Get User");
        List<String> roles = new ArrayList<>();
         User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("No user found"));
         user.getRoles().forEach(role -> roles.add(role.getName()));
        return new ClientDto(user.getEmail(),user.getPassword(),roles);
    }

}
