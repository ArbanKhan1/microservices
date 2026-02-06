package com.auth.dto;

import com.auth.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {
    private String email;
    private String password;
    private List<String> roles;
}
