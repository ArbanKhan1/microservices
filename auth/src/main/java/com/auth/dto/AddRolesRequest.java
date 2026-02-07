package com.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AddRolesRequest {
    private List<String> roles = new ArrayList<>();
}
