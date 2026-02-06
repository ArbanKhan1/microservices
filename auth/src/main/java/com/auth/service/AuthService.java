package com.auth.service;

import com.auth.dto.AddRolesRequest;
import com.auth.dto.UserRequest;
import com.auth.dto.UserResponse;
import com.auth.model.Role;
import com.auth.model.User;
import com.auth.repository.RoleRepository;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public List<String> addRole(AddRolesRequest roles) {

        if (roles.getRoles()==null) {
            throw new RuntimeException("No Roles Found");
        }

        List<Role> oldRoles = roleRepository.findByNameIn(roles.getRoles());
        List<String> newRoles = new ArrayList<>();

        for (String role: roles.getRoles()) {
            if (oldRoles.stream().noneMatch(r->r.getName().equals(role))) {
                Role addRole = new Role();
                addRole.setName(role);
                roleRepository.save(addRole);
                newRoles.add(role);
            }

        }

        return newRoles;

    }

    public UserResponse createUser(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("User Already Exists");
        }

        User user = new User();
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(userRequest,user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       userRequest.getRoles().forEach(r->{
           Role role = roleRepository.findByName(r).orElseThrow(() -> new RuntimeException("No Role Found"));
           user.getRoles().add(role);
       });
        userRepository.save(user);
        response.setEmail(user.getEmail());
        user.getRoles().forEach(role -> response.getRoles().add(role.getName()));

        return response;
    }
}
