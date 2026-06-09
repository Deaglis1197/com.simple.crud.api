package com.simple.crud.api.usermanagement.role.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;
    private Set<ActionResponse> actions;
}
