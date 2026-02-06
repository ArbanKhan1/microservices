package com.auth.dto;


import com.auth.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserResponse {
    private String email;
    private Set<String> roles = new HashSet<>();
}
